// RUN_PIPELINE_TILL: FRONTEND
// FIR_IDENTICAL
package c

fun demo() {
    val bar = 51
    fun map(f : <!SYNTAX!><!SYNTAX!><!>fun<!>
    val foo = 3;
    bar <!NONE_APPLICABLE!>+<!> map { foo }
}

/* GENERATED_FIR_TAGS: additiveExpression, functionDeclaration, integerLiteral, lambdaLiteral, localFunction,
localProperty, propertyDeclaration */
