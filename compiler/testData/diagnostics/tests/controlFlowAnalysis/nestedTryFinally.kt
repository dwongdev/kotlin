// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
fun use(arg: String?) = arg

fun sample(): String? {
    try {
        if (false) {
            return "fail"
        } else {
            if (false) {
                if (false) {
                    var foo: String? = null
                    try {
                        foo = "test"
                    } catch (e: Exception) {
                        return "fail"
                    } finally {
                        use(foo) // 'foo' is initialized here
                    }
                }
                return "fail"
            }
        }
    } finally {}
    return null
}

/* GENERATED_FIR_TAGS: assignment, functionDeclaration, ifExpression, localProperty, nullableType, propertyDeclaration,
stringLiteral, tryExpression */
