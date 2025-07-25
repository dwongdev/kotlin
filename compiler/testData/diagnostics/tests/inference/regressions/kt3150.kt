// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
package aa

class Some<T>

class SomeTemplate {
    fun <T> query(some: Class<T>) = some


    // Uncomment this to get CANNOT_COMPLETE_RESOLVE error
    fun <T> query1(some: Class<T>) = some
    fun <T> query1(some: Some<T>) = some
}

fun SomeTemplate.query(f: (i: Int) -> Unit) = f
fun SomeTemplate.query1(f: (i: Int) -> Unit) = f

fun test() {
    val mapperFunction = { i: Int -> }
    SomeTemplate().query(mapperFunction)

    // TYPE_MISMATCH: Required Class<[ERROR: CANT_INFER]>, Found (kotlin.Int) -> Unit
    SomeTemplate().query { i: Int -> }
    SomeTemplate().query1 { i: Int -> }
}

/* GENERATED_FIR_TAGS: classDeclaration, funWithExtensionReceiver, functionDeclaration, functionalType, lambdaLiteral,
localProperty, nullableType, propertyDeclaration, typeParameter */
