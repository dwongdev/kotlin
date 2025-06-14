// RUN_PIPELINE_TILL: FRONTEND
// FIR_IDENTICAL
// CHECK_TYPE

// A generic funciton is always less specific than a non-generic one
fun <T> foo(t : T) : Unit {}
fun foo(i : Int) : Int = 1

fun test() {
    checkSubtype<Int>(foo(1))
    checkSubtype<Unit>(foo("s"))
}

/* GENERATED_FIR_TAGS: classDeclaration, funWithExtensionReceiver, functionDeclaration, functionalType, infix,
integerLiteral, nullableType, stringLiteral, typeParameter, typeWithExtension */
