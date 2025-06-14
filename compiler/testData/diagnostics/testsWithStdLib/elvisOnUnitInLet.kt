// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
fun foo(x: Int?) {
    // Both parts of the Elvis should be alive, see KT-7936
    x?.let {
        smth()
    }?: orElse()
}

fun smth() {}
fun orElse() {}

/* GENERATED_FIR_TAGS: elvisExpression, functionDeclaration, lambdaLiteral, nullableType, safeCall */
