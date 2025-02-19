/*
 * Copyright 2010-2024 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.asJava.classes

import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiManager
import com.intellij.psi.impl.PsiCachedValueImpl
import com.intellij.psi.util.CachedValueProvider
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class LightClassesLazyCreator(private val project: Project) : KotlinClassInnerStuffCache.LazyCreator() {
    override fun <T : Any> get(initializer: () -> T, dependencies: List<Any>) = object : Lazy<T> {
        private val lock = ReentrantLock()
        private val holder = lazyPub {
            PsiCachedValueImpl.Soft(PsiManager.getInstance(project)) {
                val v = initializer()
                CachedValueProvider.Result.create(v, dependencies)
            }
        }

        private fun computeValue(): T = holder.value.value ?: error("holder has not null in initializer")

        override val value: T
            get() {
                return if (holder.value.hasUpToDateValue()) {
                    computeValue()
                } else {
                    // the idea behind this locking approach:
                    // Thread T1 starts to calculate value for A it acquires lock for A
                    //
                    // Assumption 1: Lets say A calculation requires another value e.g. B to be calculated
                    // Assumption 2: Thread T2 wants to calculate value for B

                    // to avoid dead-lock
                    // - we mark thread as doing calculation and acquire lock only once per thread
                    // as a trade-off to prevent dependent value could be calculated several time
                    // due to CAS (within putUserDataIfAbsent etc) the same instance of calculated value will be used

                    // TODO: NOTE: acquire lock for a several seconds to avoid dead-lock via resolve is a WORKAROUND

                    val timeout = 5.toDuration(DurationUnit.SECONDS)
                    val stepTimeout = 500.toDuration(DurationUnit.MILLISECONDS)

                    var calculatedValue: T? = null
                    for (attempt in 0 until (timeout.inWholeMilliseconds / stepTimeout.inWholeMilliseconds)) {
                        if (calculatedValue != null || initIsRunning.get()) break

                        ProgressManager.checkCanceled()

                        if (lock.tryLock(stepTimeout.inWholeMilliseconds, TimeUnit.MILLISECONDS)) {
                            try {
                                initIsRunning.set(true)
                                try {
                                    calculatedValue = computeValue()
                                } finally {
                                    initIsRunning.set(false)
                                }
                            } finally {
                                lock.unlock()
                            }
                        }
                    }

                    calculatedValue ?: computeValue()
                }
            }

        override fun isInitialized() = holder.isInitialized()
    }

    companion object {
        @JvmStatic
        private val initIsRunning: ThreadLocal<Boolean> = ThreadLocal.withInitial { false }
    }
}