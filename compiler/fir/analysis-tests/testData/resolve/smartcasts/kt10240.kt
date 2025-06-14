// RUN_PIPELINE_TILL: BACKEND
fun a(): Int {
    fun b(): Int {
        var c: Int? = null
        if (c == null ||
            0 < c // FE 1.0: smart cast impossible, see KT-10240
        ) c = 0
        return c <!USELESS_ELVIS!>?: 0<!>
    }

    var c: Int = 0
    c = 0
    return c
}

fun myRun(f: () -> Unit) {
    f()
}

fun println(arg: Int) {}

fun d() {
    myRun {
        var koko: String? = "Alpha"
        while (koko != null) {
            println(koko.length)
            koko = null
        }
    }
    myRun {
        var koko: String? = "Omega"
        while (koko != null) {
            println(koko.length) // FE 1.0: smart cast impossible, see KT-19446
            koko = null
        }
    }
}

/* GENERATED_FIR_TAGS: assignment, comparisonExpression, disjunctionExpression, elvisExpression, equalityExpression,
functionDeclaration, functionalType, ifExpression, integerLiteral, lambdaLiteral, localFunction, localProperty,
nullableType, propertyDeclaration, smartcast, stringLiteral, whileLoop */
