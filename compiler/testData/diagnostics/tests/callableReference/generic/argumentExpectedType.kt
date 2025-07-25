// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
// DIAGNOSTICS: -UNUSED_PARAMETER

class Case<T>
fun <T> test(case: Case<T>) {}
fun runTest(method: (Case<Any>) -> Unit) {}

fun <T> runTestGeneric(f: (Case<T>) -> Unit) {}

fun test() {
    runTest(::test)
    runTestGeneric<Int>(::test)
}

/* GENERATED_FIR_TAGS: callableReference, classDeclaration, functionDeclaration, functionalType, nullableType,
typeParameter */
