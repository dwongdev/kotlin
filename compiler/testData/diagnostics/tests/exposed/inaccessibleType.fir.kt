// RUN_PIPELINE_TILL: BACKEND
// DIAGNOSTICS: -USELESS_CAST -UNUSED_PARAMETER -UNUSED_VARIABLE
// LANGUAGE: -ForbidInferOfInvisibleTypeAsReifiedVarargOrReturnType

// FILE: j/Base.java
package j;
public interface Base {
    void foo();
}

// FILE: j/Impl.java
package j;

/* package */ abstract class Impl implements Base {
    public void foo() {}
}

// FILE: j/Derived1.java
package j;

public class Derived1 extends Impl {}

// FILE: j/Derived2.java
package j;

public class Derived2 extends Impl {}

// FILE: k/Client.kt
package k

import j.*

val d1 = Derived1()
val d2 = Derived2()

fun <T> select(x1: T, x2: T) = x1
fun <T> selectn(vararg xx: T) = xx[0]
fun <T : Base> foo(x: T) = x.foo()
fun <T> listOf2(x1: T, x2: T): List<T> = null!!
fun <T> arrayOf2(x1: T, x2: T): Array<T> = null!!

fun test() {
    val test1: Base = if (true) d1 else d2

    val test2 = <!INFERRED_INVISIBLE_WHEN_TYPE_WARNING!>if (true) d1 else d2<!>

    val test3 = <!INFERRED_INVISIBLE_WHEN_TYPE_WARNING!>when {
        true -> d1
        else -> d2
    }<!>

    val test4: Base = when {
        true -> d1
        else -> d2
    }

    val test5 = <!INFERRED_INVISIBLE_RETURN_TYPE_WARNING!>select(d1, d2)<!>

    val test6 = select<Base>(d1, d2)

    val test7 = select(d1 as Base, d2)

    val test8 = <!INFERRED_INVISIBLE_VARARG_TYPE_ARGUMENT_WARNING!>selectn<!>(d1, d2)

    val test9 = selectn<Base>(d1, d2)

    val test10 = <!INFERRED_INVISIBLE_RETURN_TYPE_WARNING!>listOf2(d1, d2)<!>

    val test11: List<Base> = <!INFERRED_INVISIBLE_RETURN_TYPE_WARNING!>listOf2(d1, d2)<!>
    // NB Inferred type is List<Impl> because List is covariant.

    val test12 = listOf2<Base>(d1, d2)

    val test13 = <!INFERRED_INVISIBLE_RETURN_TYPE_WARNING!>arrayOf2(d1, d2)<!>

    val test14: Array<Base> = arrayOf2(d1, d2)
    // NB Inferred type is Array<Base> because Array is invariant.

    val test15 = arrayOf2<Base>(d1, d2)

    for (test16 in <!INFERRED_INVISIBLE_RETURN_TYPE_WARNING!>listOf2(d1, d2)<!>) {}
}

fun testOkInJava() {
    // The following is Ok in Java, but is an error in Kotlin.
    // TODO do not generate unneeded CHECKCASTs.
    // TODO do not report INACCESSIBLE_TYPE for corresponding cases.
    <!INFERRED_INVISIBLE_RETURN_TYPE_WARNING!>select(d1, d2)<!>
    foo(<!INFERRED_INVISIBLE_RETURN_TYPE_WARNING!>select(d1, d2)<!>)
}

/* GENERATED_FIR_TAGS: asExpression, capturedType, checkNotNullCall, forLoop, functionDeclaration, ifExpression,
integerLiteral, javaFunction, javaType, localProperty, nullableType, outProjection, propertyDeclaration, typeConstraint,
typeParameter, vararg, whenExpression */
