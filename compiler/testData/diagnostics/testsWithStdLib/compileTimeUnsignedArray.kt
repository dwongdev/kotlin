// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
// ISSUE: KT-57211

@file:OptIn(ExperimentalUnsignedTypes::class)

annotation class Ann(
    val u: UInt,
    val uba: UByteArray,
    val usa: UShortArray,
    val uia: UIntArray,
    val ula: ULongArray,
)

@OptIn(ExperimentalUnsignedTypes::class)
@Ann(
    1u,
    [1u],
    ushortArrayOf(),
    [1u, 1u],
    ulongArrayOf(1u, 1u),
)
fun foo() {}

/* GENERATED_FIR_TAGS: annotationDeclaration, annotationUseSiteTargetFile, classReference, collectionLiteral,
functionDeclaration, primaryConstructor, propertyDeclaration, unsignedLiteral */
