// RUN_PIPELINE_TILL: FRONTEND
// FILE: J.java

import org.jetbrains.annotations.*;

public class J {
    void foo(String x) {}
    void foo(@NotNull Double x) {}
    void foo(@Nullable Byte x) {}
}

// FILE: test.kt

fun test(j: J, nullStr: String?, nullByte: Byte?, nullDouble: Double?) {
    j.foo(nullStr)
    j.foo(<!ARGUMENT_TYPE_MISMATCH!>nullDouble<!>)
    j.foo(nullByte)
}

/* GENERATED_FIR_TAGS: functionDeclaration, javaFunction, javaType, nullableType */
