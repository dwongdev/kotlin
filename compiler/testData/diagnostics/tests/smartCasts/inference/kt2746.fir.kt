// RUN_PIPELINE_TILL: BACKEND
//KT-2746 Do.smartcasts in inference

class C<T>(t :T)

fun test1(a: Any) {
    if (a is String) {
        val c: C<String> = C(a)
    }
}


fun <T> f(t :T): C<T> = C(t)

fun test2(a: Any) {
    if (a is String) {
        val c1: C<String> = f(a)
    }
}

/* GENERATED_FIR_TAGS: classDeclaration, functionDeclaration, ifExpression, isExpression, localProperty, nullableType,
primaryConstructor, propertyDeclaration, smartcast, typeParameter */
