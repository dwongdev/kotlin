// RUN_PIPELINE_TILL: FRONTEND
// FIR_IDENTICAL
// CHECK_TYPE

interface Tr<T>
interface G<T> : Tr<T>

fun test(tr: Tr<String>?) {
    val v = tr as G?
    // If v is not nullable, there will be a warning on this line:
    checkSubtype<G<String>>(v!!)
}

/* GENERATED_FIR_TAGS: asExpression, checkNotNullCall, classDeclaration, funWithExtensionReceiver, functionDeclaration,
functionalType, infix, interfaceDeclaration, localProperty, nullableType, propertyDeclaration, typeParameter,
typeWithExtension */
