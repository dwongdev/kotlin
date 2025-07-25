// IGNORE_FIR_DIAGNOSTICS
// RUN_PIPELINE_TILL: BACKEND
// MODULE: m1-common
// FILE: common.kt

<!EXPECTED_PRIVATE_DECLARATION, EXPECTED_PRIVATE_DECLARATION{JVM}!>private<!> expect fun foo()
<!EXPECTED_PRIVATE_DECLARATION, EXPECTED_PRIVATE_DECLARATION{JVM}!>private<!> expect val bar: String
<!EXPECTED_PRIVATE_DECLARATION, EXPECTED_PRIVATE_DECLARATION{JVM}!>private<!> expect fun Int.memExt(): Any

<!EXPECTED_PRIVATE_DECLARATION, EXPECTED_PRIVATE_DECLARATION{JVM}!>private<!> expect class Foo

// MODULE: m2-jvm()()(m1-common)
// FILE: jvm.kt

private actual fun foo() {}
private actual val bar: String = ""
private actual fun Int.memExt(): Any = 0

private actual class Foo

/* GENERATED_FIR_TAGS: actual, classDeclaration, expect, funWithExtensionReceiver, functionDeclaration, integerLiteral,
propertyDeclaration, stringLiteral */
