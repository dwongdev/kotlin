/*
 * Copyright 2010-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */
// WITH_STDLIB

import kotlin.test.*

val sb = StringBuilder()

fun box(): String {
    run {
        sb.append("OK")
    }
    return sb.toString()
}

fun run(f: () -> Unit) {
    f()
}
