// RUN_PIPELINE_TILL: FRONTEND
interface A

interface B

fun test1(): B = <!RETURN_TYPE_MISMATCH!>object : A {
}<!>

fun test2(): B = <!RETURN_TYPE_MISMATCH!>object {
}<!>

/* GENERATED_FIR_TAGS: anonymousObjectExpression, functionDeclaration, interfaceDeclaration */
