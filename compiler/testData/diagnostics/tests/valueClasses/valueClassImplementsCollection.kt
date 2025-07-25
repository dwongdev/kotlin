// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
// SKIP_JAVAC
// LANGUAGE: +InlineClasses
// ALLOW_KOTLIN_PACKAGE

package kotlin.jvm

annotation class JvmInline

@JvmInline
value class UInt(val x: Int)

@JvmInline
value class UIntArray(private val storage: IntArray) : Collection<UInt> {
    public override val size: Int get() = storage.size

    override operator fun iterator() = TODO()
    override fun contains(element: UInt): Boolean = TODO()
    override fun containsAll(elements: Collection<UInt>): Boolean = TODO()
    override fun isEmpty(): Boolean = TODO()
}

/* GENERATED_FIR_TAGS: annotationDeclaration, classDeclaration, functionDeclaration, getter, operator, override,
primaryConstructor, propertyDeclaration, value */
