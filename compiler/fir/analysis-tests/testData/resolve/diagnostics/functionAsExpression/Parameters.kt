// RUN_PIPELINE_TILL: FRONTEND
val bar = fun(p: Int = <!ANONYMOUS_FUNCTION_PARAMETER_WITH_DEFAULT_VALUE!>3<!>) {}
val bas = fun(<!USELESS_VARARG_ON_PARAMETER!>vararg p: Int<!>) {}

fun gar() = fun(p: Int = <!ANONYMOUS_FUNCTION_PARAMETER_WITH_DEFAULT_VALUE!>3<!>) {}
fun gas() = fun(<!USELESS_VARARG_ON_PARAMETER!>vararg p: Int<!>) {}

fun outer(b: Any?) {
    val bar = fun(p: Int = <!ANONYMOUS_FUNCTION_PARAMETER_WITH_DEFAULT_VALUE!>3<!>) {}
    val bas = fun(<!USELESS_VARARG_ON_PARAMETER!>vararg p: Int<!>) {}

    fun gar() = fun(p: Int = <!ANONYMOUS_FUNCTION_PARAMETER_WITH_DEFAULT_VALUE!>3<!>) {}
    fun gas() = fun(<!USELESS_VARARG_ON_PARAMETER!>vararg p: Int<!>) {}

    outer(fun(p: Int = <!ANONYMOUS_FUNCTION_PARAMETER_WITH_DEFAULT_VALUE!>3<!>) {})
    outer(fun(<!USELESS_VARARG_ON_PARAMETER!>vararg p: Int<!>) {})
}

/* GENERATED_FIR_TAGS: anonymousFunction, functionDeclaration, integerLiteral, localFunction, localProperty,
nullableType, propertyDeclaration, vararg */
