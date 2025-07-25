// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
// SKIP_TXT
class C<T>(val value: T?)

fun <T> assignable(x: () -> T) {}

fun <V> foo(t: C<out Any>, v: C<out V>) {
    assignable<V?> { v.value }
    if (t == v) {
        // `value: CapturedType(out V)?` <: `value: CapturedType(out Any)?` - same instantiation
        assignable<V?> { v.value }
        assignable<V?> { t.value }
    }
}

/* GENERATED_FIR_TAGS: capturedType, classDeclaration, equalityExpression, functionDeclaration, functionalType,
ifExpression, intersectionType, lambdaLiteral, nullableType, outProjection, primaryConstructor, propertyDeclaration,
smartcast, typeParameter */
