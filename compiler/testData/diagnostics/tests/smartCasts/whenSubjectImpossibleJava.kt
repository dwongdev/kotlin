// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
// See KT-10061
// FILE: My.java
public class My {
    String getSomething() { return "xyz"; }
}

// FILE: My.kt
fun foo(my: My) {
    my.something!!
    when (my.something) { }
}

/* GENERATED_FIR_TAGS: checkNotNullCall, flexibleType, functionDeclaration, javaProperty, javaType, whenExpression,
whenWithSubject */
