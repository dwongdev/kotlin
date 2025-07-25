// RUN_PIPELINE_TILL: FRONTEND
/*
 * KOTLIN DIAGNOSTICS SPEC TEST (NEGATIVE)
 *
 * SPEC VERSION: 0.1-313
 * PRIMARY LINKS: expressions, when-expression -> paragraph 5 -> sentence 1
 * expressions, when-expression -> paragraph 6 -> sentence 1
 * expressions, when-expression -> paragraph 6 -> sentence 5
 * expressions, when-expression, exhaustive-when-expressions -> paragraph 2 -> sentence 9
 */

enum class MyEnum {
    A, B, C
}

fun foo(x: MyEnum): Int {
    return when (x) {
        MyEnum.A -> 1
        is <!IS_ENUM_ENTRY!>MyEnum.<!ENUM_ENTRY_AS_TYPE!>B<!><!> -> 2
        is <!IS_ENUM_ENTRY!>MyEnum.<!ENUM_ENTRY_AS_TYPE!>C<!><!> -> 3
    }
}

/* GENERATED_FIR_TAGS: enumDeclaration, enumEntry, equalityExpression, functionDeclaration, integerLiteral, isExpression,
smartcast, whenExpression, whenWithSubject */
