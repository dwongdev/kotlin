// RUN_PIPELINE_TILL: BACKEND
// DIAGNOSTICS: -UNUSED_PARAMETER

object Inference2 {
    interface Foo<T>

    fun <T> delegate(): Foo<T> = TODO()

    operator fun <T> Foo<T>.provideDelegate(host: T, p: Any?): Foo<T> = TODO()
    operator fun <T> Foo<T>.getValue(receiver: Inference2, p: Any?): String = TODO()

    val test1: String by delegate() // same story like in Inference1
    val test2: String by delegate<Inference2>()
}

/* GENERATED_FIR_TAGS: funWithExtensionReceiver, functionDeclaration, interfaceDeclaration, nestedClass, nullableType,
objectDeclaration, operator, propertyDeclaration, propertyDelegate, typeParameter */
