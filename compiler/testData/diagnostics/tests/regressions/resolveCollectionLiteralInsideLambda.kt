// RUN_PIPELINE_TILL: FRONTEND
// DIAGNOSTICS: -UNUSED_PARAMETER

fun foo(l: () -> Unit) {}
fun bar(l: () -> String) {}

val a = foo { <!NEW_INFERENCE_NO_INFORMATION_FOR_PARAMETER, UNSUPPORTED!>[]<!> }
val b = bar { <!TYPE_MISMATCH, TYPE_MISMATCH, UNSUPPORTED!>[]<!> }

/* GENERATED_FIR_TAGS: collectionLiteral, functionDeclaration, functionalType, lambdaLiteral, propertyDeclaration */
