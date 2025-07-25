// RUN_PIPELINE_TILL: BACKEND
fun <T> myRun(block: () -> T): T = block()

fun foo() {}

fun test() {
    myRun {
        try {
            val x = 1
        } catch(e: Exception) {
            foo()
        }
    }
}

/* GENERATED_FIR_TAGS: functionDeclaration, functionalType, integerLiteral, lambdaLiteral, localProperty, nullableType,
propertyDeclaration, tryExpression, typeParameter */
