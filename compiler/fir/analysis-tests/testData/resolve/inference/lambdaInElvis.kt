// RUN_PIPELINE_TILL: BACKEND
// ISSUE: KT-39012

interface A

fun <T> foo(f: (MutableList<T>) -> Unit): List<T>? = TODO()
fun <T> listOf(): List<T> = TODO()

fun bar1(w: List<CharSequence>): List<CharSequence>? {
    return foo { container ->
        container.add("")
    } ?: w
}

fun bar2(): List<CharSequence>? {
    return foo { container ->
        container.add("")
    } ?: listOf()
}

/* GENERATED_FIR_TAGS: elvisExpression, functionDeclaration, functionalType, interfaceDeclaration, lambdaLiteral,
nullableType, stringLiteral, typeParameter */
