// RUN_PIPELINE_TILL: FRONTEND
// FIR_IDENTICAL
// SKIP_TXT
// LANGUAGE: -ParseLambdaWithSuspendModifier

fun bar() {
    suspend {
        println()
    }

    @Ann suspend {
        println()
    }

    suspend @Ann {
        println()
    }

    kotlin.<!NON_MODIFIER_FORM_FOR_BUILT_IN_SUSPEND!>suspend<!> {

    }

    <!NON_MODIFIER_FORM_FOR_BUILT_IN_SUSPEND!>suspend<!>() {
        println()
    }

    <!NON_MODIFIER_FORM_FOR_BUILT_IN_SUSPEND!>suspend<!>({ println() })

    <!NON_MODIFIER_FORM_FOR_BUILT_IN_SUSPEND!>suspend<!><Unit> {
        println()
    }

    val w: (suspend () -> Int) -> Any? = ::<!NON_MODIFIER_FORM_FOR_BUILT_IN_SUSPEND!>suspend<!>
}

@Target(AnnotationTarget.EXPRESSION)
@Retention(AnnotationRetention.SOURCE)
annotation class Ann

fun main(suspend: WLambdaInvoke) {

    <!MODIFIER_FORM_FOR_NON_BUILT_IN_SUSPEND!>suspend<!> {}
}

class WLambdaInvoke {
    operator fun invoke(l: () -> Unit) {}
}

/* GENERATED_FIR_TAGS: annotationDeclaration, callableReference, classDeclaration, functionDeclaration, functionalType,
lambdaLiteral, localProperty, nullableType, operator, propertyDeclaration, suspend */
