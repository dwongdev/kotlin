// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
// OPT_IN: kotlin.RequiresOptIn
// DIAGNOSTICS: -UNUSED_EXPRESSION -UNUSED_PARAMETER -UNUSED_VARIABLE

@file:OptIn(ExperimentalTypeInference::class)

import kotlin.experimental.ExperimentalTypeInference

class GenericController<T> {
    suspend fun yield(t: T) {}
}

fun <S, P1, P2, R> generate(p1: P1, p2: List<P2>, g: suspend GenericController<S>.(P1, P2) -> R): Four<S, P1, P2, R> = TODO()

val test1 = generate(1, listOf("")) { p1, p2 ->
    yield(p1)

    p2
}

fun <X> listOf(vararg x: X): List<X> = TODO()
class Four<X, Y, Z, T>

/* GENERATED_FIR_TAGS: annotationUseSiteTargetFile, classDeclaration, classReference, functionDeclaration,
functionalType, integerLiteral, lambdaLiteral, nullableType, propertyDeclaration, stringLiteral, suspend, typeParameter,
typeWithExtension, vararg */
