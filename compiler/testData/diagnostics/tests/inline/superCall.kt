// RUN_PIPELINE_TILL: FRONTEND
// FILE: 1.kt

package test

open class A {
    open fun test() = "OK"
}

object X : A() {
    override fun test(): String {
        return "fail"
    }

    <!NOTHING_TO_INLINE!>inline<!> fun doTest(): String {
        return <!SUPER_CALL_FROM_PUBLIC_INLINE_ERROR!>super.test()<!>
    }
}

// FILE: 2.kt

import test.*

fun box(): String {
    return X.doTest()
}

/* GENERATED_FIR_TAGS: classDeclaration, functionDeclaration, inline, objectDeclaration, override, stringLiteral,
superExpression */
