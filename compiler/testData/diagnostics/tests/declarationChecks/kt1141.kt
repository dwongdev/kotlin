// RUN_PIPELINE_TILL: FRONTEND
// FIR_IDENTICAL
//KT-1141 No check that object in 'object expression' implements all abstract members of supertype

package kt1141

public interface SomeTrait {
    fun foo()
}

fun foo() {
    val x = <!ABSTRACT_MEMBER_NOT_IMPLEMENTED!>object<!> : SomeTrait {
    }
    x.foo()
}

<!ABSTRACT_MEMBER_NOT_IMPLEMENTED!>object Rr<!> : SomeTrait {}

<!ABSTRACT_MEMBER_NOT_IMPLEMENTED!>class C<!> : SomeTrait {}

fun foo2() {
    val r = <!ABSTRACT_MEMBER_NOT_IMPLEMENTED!>object<!> : Runnable {} //no error
}

/* GENERATED_FIR_TAGS: anonymousObjectExpression, classDeclaration, functionDeclaration, interfaceDeclaration,
localProperty, objectDeclaration, propertyDeclaration */
