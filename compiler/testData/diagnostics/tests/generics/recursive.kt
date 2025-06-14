// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
// DIAGNOSTICS: -ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE -UNUSED_VALUE

interface Rec<T>
class A : Rec<A>
class B : Rec<B>

fun test(a: A, b: B, c: Boolean) {
    var ab = if (c) a else b
    ab = a
    ab = b
}

/* GENERATED_FIR_TAGS: assignment, classDeclaration, functionDeclaration, ifExpression, interfaceDeclaration,
localProperty, nullableType, propertyDeclaration, starProjection, typeParameter */
