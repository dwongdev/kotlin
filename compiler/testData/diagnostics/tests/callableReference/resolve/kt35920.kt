// RUN_PIPELINE_TILL: BACKEND
// SKIP_TXT
// DIAGNOSTICS: -UNUSED_PARAMETER
// FILE: JavaClass.java
public class JavaClass<R> {
    public static String baz(int x) { return ""; }
}

// FILE: main.kt
fun foo(x: (Int) -> String) {}

fun main() {
    foo(JavaClass<*>::<!UNRESOLVED_REFERENCE!>baz<!>)
}

/* GENERATED_FIR_TAGS: flexibleType, functionDeclaration, functionalType, javaCallableReference */
