/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

@file:Suppress("FunctionName")

package org.jetbrains.kotlin.gradle.unitTests

import org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginLifecycle
import org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginLifecycle.Stage.*
import org.jetbrains.kotlin.gradle.plugin.kotlinMultiplatformPluginLifecycle
import org.jetbrains.kotlin.gradle.util.buildProjectWithMPP
import org.junit.Test
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import kotlin.test.*

class KotlinMultiplatformPluginLifecycleTest {

    private val project = buildProjectWithMPP()
    private val lifecycle = project.kotlinMultiplatformPluginLifecycle

    @Test
    fun `test - configure phase is executed right away`() {
        val invocations = AtomicInteger(0)
        lifecycle.enqueue(Configure) {
            invocations.incrementAndGet()
        }
        assertEquals(1, invocations.get(), "Expected one invocation")
    }

    @Test
    fun `test - configure phase - nested enqueue - is executed as queue`() {
        val outerInvocations = AtomicInteger(0)
        val nestedAInvocations = AtomicInteger(0)
        val nestedBInvocations = AtomicInteger(0)
        val nestedCInvocations = AtomicInteger(0)
        lifecycle.enqueue(Configure) {
            assertEquals(1, outerInvocations.incrementAndGet())

            lifecycle.enqueue(Configure) nestedA@{
                assertEquals(0, nestedBInvocations.get(), "Expected nestedA to be executed before nestedB")
                assertEquals(1, nestedAInvocations.incrementAndGet())

                lifecycle.enqueue(Configure) nestedC@{
                    assertEquals(1, nestedBInvocations.get(), "Expected nestedB to be executed before nestedC")
                    assertEquals(1, nestedCInvocations.incrementAndGet())
                }
            }

            lifecycle.enqueue(Configure) nestedB@{
                assertEquals(1, nestedAInvocations.get(), "Expected nestedA to be executed before nestedB")
                assertEquals(0, nestedCInvocations.get(), "Expected nestedB to be executed before nestedC")
                assertEquals(1, nestedBInvocations.incrementAndGet())
            }
        }

        assertEquals(1, outerInvocations.get())
        assertEquals(1, nestedAInvocations.get())
        assertEquals(1, nestedBInvocations.get())
        assertEquals(1, nestedCInvocations.get())
    }

    @Test
    fun `test - all stages are executed in order`() {
        val invocations = KotlinMultiplatformPluginLifecycle.Stage.values().associateWith { AtomicInteger(0) }
        KotlinMultiplatformPluginLifecycle.Stage.values().toList().forEach { stage ->
            lifecycle.enqueue(stage) {
                KotlinMultiplatformPluginLifecycle.Stage.values().forEach { otherStage ->
                    when {
                        otherStage.ordinal < stage.ordinal -> assertEquals(1, invocations.getValue(otherStage).get())
                        otherStage.ordinal == stage.ordinal -> assertEquals(1, invocations.getValue(stage).incrementAndGet())
                        else -> assertEquals(0, invocations.getValue(otherStage).get())
                    }
                }
            }
        }

        project.evaluate()

        invocations.forEach { (stage, invocations) ->
            assertEquals(1, invocations.get(), "Expected stage '$stage' to be executed")
        }
    }

    @Test
    fun `test - afterEvaluate based stage executes queue in order`() {
        val action1Invocations = AtomicInteger(0)
        val action2Invocations = AtomicInteger(0)
        val action3Invocations = AtomicInteger(0)

        lifecycle.enqueue(Finalised) action3@{
            assertEquals(1, action1Invocations.get(), "Expected action1 to be executed before action3")
            assertEquals(1, action2Invocations.get(), "Expected action2 to be executed before action3")
            assertEquals(1, action3Invocations.incrementAndGet())
        }

        lifecycle.enqueue(AfterEvaluate) action1@{
            assertEquals(0, action2Invocations.get(), "Expected action1 to be executed before action2")
            assertEquals(0, action3Invocations.get(), "Expected action1 to be executed before action3")
            assertEquals(1, action1Invocations.incrementAndGet())
        }

        lifecycle.enqueue(AfterEvaluate) action2@{
            assertEquals(1, action1Invocations.get(), "Expected action1 to be executed before action2")
            assertEquals(0, action3Invocations.get(), "Expected action2 to be executed before action3")
            assertEquals(1, action2Invocations.incrementAndGet())
        }

        assertEquals(0, action1Invocations.get())
        assertEquals(0, action2Invocations.get())
        assertEquals(0, action3Invocations.get())

        project.evaluate()

        assertEquals(1, action1Invocations.get())
        assertEquals(1, action2Invocations.get())
        assertEquals(1, action3Invocations.get())
    }

    @Test
    fun `test - afterEvaluate based stage - allows enqueue in current stage`() {
        val outerInvocations = AtomicInteger(0)
        val nestedAInvocations = AtomicInteger(0)
        val nestedBInvocations = AtomicInteger(0)
        val nestedCInvocations = AtomicInteger(0)
        lifecycle.enqueue(AfterEvaluate) {
            assertEquals(1, outerInvocations.incrementAndGet())

            lifecycle.enqueue(AfterEvaluate) nestedA@{
                assertEquals(0, nestedBInvocations.get(), "Expected nestedA to be executed before nestedB")
                assertEquals(1, nestedAInvocations.incrementAndGet())

                lifecycle.enqueue(AfterEvaluate) nestedC@{
                    assertEquals(1, nestedBInvocations.get(), "Expected nestedB to be executed before nestedC")
                    assertEquals(1, nestedCInvocations.incrementAndGet())
                }
            }

            lifecycle.enqueue(AfterEvaluate) nestedB@{
                assertEquals(1, nestedAInvocations.get(), "Expected nestedA to be executed before nestedB")
                assertEquals(0, nestedCInvocations.get(), "Expected nestedB to be executed before nestedC")
                assertEquals(1, nestedBInvocations.incrementAndGet())
            }
        }

        assertEquals(0, outerInvocations.get())
        assertEquals(0, nestedAInvocations.get())
        assertEquals(0, nestedBInvocations.get())
        assertEquals(0, nestedCInvocations.get())

        project.evaluate()

        assertEquals(1, outerInvocations.get())
        assertEquals(1, nestedAInvocations.get())
        assertEquals(1, nestedBInvocations.get())
        assertEquals(1, nestedCInvocations.get())
    }

    @Test
    fun `test - enqueue of already executed stage - throws exception`() {
        val executed = AtomicBoolean(false)
        lifecycle.enqueue(Finalised) {
            assertFailsWith<KotlinMultiplatformPluginLifecycle.IllegalLifecycleException> {
                lifecycle.enqueue(AfterEvaluate) { fail("This code shall not be executed!") }
            }
            assertFalse(executed.getAndSet(true))
        }

        project.evaluate()
        assertTrue(executed.get())
    }

    @Test
    fun `test - stage property is correct`() {
        KotlinMultiplatformPluginLifecycle.Stage.values().forEach { stage ->
            lifecycle.enqueue(stage) {
                assertEquals(lifecycle.stage, stage)
            }
        }
        project.evaluate()
    }

    @Test
    fun `test - invoke configure twice`() {
        val action1Invocations = AtomicInteger(0)
        val action2Invocations = AtomicInteger(0)
        val action3Invocations = AtomicInteger(0)

        lifecycle.enqueue(Configure) action1@{
            assertEquals(0, action2Invocations.get())
            assertEquals(0, action3Invocations.get())
            assertEquals(1, action1Invocations.incrementAndGet())
        }

        lifecycle.enqueue(Configure) action2@{
            lifecycle.enqueue(Configure) action3@{
                assertEquals(1, action1Invocations.get())
                assertEquals(1, action2Invocations.get())
                assertEquals(1, action3Invocations.incrementAndGet())
            }

            assertEquals(1, action1Invocations.get())
            assertEquals(0, action3Invocations.get())
            assertEquals(1, action2Invocations.incrementAndGet())
        }

        assertEquals(1, action1Invocations.get())
        assertEquals(1, action2Invocations.get())
        assertEquals(1, action3Invocations.get())
    }
}