// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
// DIAGNOSTICS: -UNUSED_PARAMETER
fun <R> foo(x: R, y: String) {}
fun <R> foo(x: R, y: R) {
    foo<R>(x, "") // ok, resolved foo(x: R, y: String)
    foo(x, "") // ok, foo(x: R, y: String)
    foo<R>(x, x) // ok, resolved foo(x: R, y: R)
    foo(x, x) // ok, resolved foo(x: R, y: R)
}

class Q<R>(x: R) {
    fun foo() {
        Q<R>("")
    }
}
fun <R> Q(x: String) {}

/* GENERATED_FIR_TAGS: classDeclaration, functionDeclaration, nullableType, primaryConstructor, stringLiteral,
typeParameter */
