// RUN_PIPELINE_TILL: FRONTEND
// FIR_IDENTICAL
@Target(AnnotationTarget.CLASS)
annotation class My
data class Pair(val a: Int, val b: Int)
fun foo(): Int {
    val (<!WRONG_ANNOTATION_TARGET!>@My<!> <!WRONG_MODIFIER_TARGET!>private<!> a, <!WRONG_ANNOTATION_TARGET!>@My<!> <!WRONG_MODIFIER_TARGET!>public<!> b) = Pair(12, 34)
    return a + b
}

/* GENERATED_FIR_TAGS: additiveExpression, annotationDeclaration, classDeclaration, data, destructuringDeclaration,
functionDeclaration, integerLiteral, localProperty, primaryConstructor, propertyDeclaration */
