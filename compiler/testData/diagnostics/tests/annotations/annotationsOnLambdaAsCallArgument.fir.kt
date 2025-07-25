// RUN_PIPELINE_TILL: FRONTEND
@Target(AnnotationTarget.EXPRESSION)
@Retention(AnnotationRetention.SOURCE)
@Repeatable
annotation class Ann(val x: Int = 1)

inline fun bar(block: () -> Int): Int = block()

fun foo() {
    bar() @Ann(1) @Ann(2) { 101 }
    bar() @Ann(3) { 102 }

    bar @Ann l1@ {
        return@l1 103
    }

    bar @Ann(<!ARGUMENT_TYPE_MISMATCH!>""<!>) {
        104
    }
}

/* GENERATED_FIR_TAGS: annotationDeclaration, functionDeclaration, functionalType, inline, integerLiteral, lambdaLiteral,
primaryConstructor, propertyDeclaration, stringLiteral */
