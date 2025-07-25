// RUN_PIPELINE_TILL: BACKEND
// FILE: Base.kt

interface Base {
    val x: Int
}

// FILE: Inheritor.java

public class Inheritor implements Base {
    public int getX() {
        return 42;
    }
}

// FILE: Test.kt

class Tester : Inheritor() {
    fun test(): Int {
        return x
    }
}

/* GENERATED_FIR_TAGS: classDeclaration, functionDeclaration, interfaceDeclaration, javaProperty, javaType,
propertyDeclaration */
