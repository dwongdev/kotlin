// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
public interface Base {
    fun test() = "OK"
}

public interface Base2 : Base 

class Delegate : Base2

fun box(): String {
    object : Base, Base2 by Delegate() {

    }

    object : Base2, Base by Delegate() {
    
    }

    return "OK"
}

/* GENERATED_FIR_TAGS: anonymousObjectExpression, classDeclaration, functionDeclaration, inheritanceDelegation,
interfaceDeclaration, stringLiteral */
