// FIR_IDENTICAL
// !DIAGNOSTICS: -UNUSED_VARIABLE
// MODULE: m1
// FILE: a.kt

package p

public class A {
    internal val a = A()
    internal var v = A()
    internal fun a() = A()
    internal inner class B
}

internal val a = A()
internal var v = A()
internal fun a() = A()
internal class B

// MODULE: m2()(m1)
// FILE: b.kt

import p.*

fun test() {
    val _a = a
    val _v = v
    a()
    B()

    val inst = A()
    val ia = inst.a
    val iv = inst.v
    inst.a()
    inst.B()
}

