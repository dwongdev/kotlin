// RUN_PIPELINE_TILL: FRONTEND
// FIR_IDENTICAL
fun trans(n: Int, f: () -> Boolean) = if (f()) n else null

fun foo() {
    var i: Int? = 5
    if (i != null) {
        fun can(): Boolean {
            i = null
            return true
        }
        <!SMARTCAST_IMPOSSIBLE!>i<!>.hashCode()
        trans(<!SMARTCAST_IMPOSSIBLE!>i<!>, ::can)
        <!SMARTCAST_IMPOSSIBLE!>i<!>.hashCode()
    }
}

/* GENERATED_FIR_TAGS: assignment, callableReference, equalityExpression, functionDeclaration, functionalType,
ifExpression, integerLiteral, localFunction, localProperty, nullableType, propertyDeclaration, smartcast */
