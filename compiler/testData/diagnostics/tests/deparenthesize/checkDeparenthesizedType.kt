// RUN_PIPELINE_TILL: FRONTEND
// CHECK_TYPE

package m

import checkSubtype

fun test(i: Int?) {
    if (i != null) {
        foo(<!REDUNDANT_LABEL_WARNING!>l1@<!> <!DEBUG_INFO_SMARTCAST!>i<!>)
        foo((<!DEBUG_INFO_SMARTCAST!>i<!>))
        foo(<!REDUNDANT_LABEL_WARNING!>l2@<!> (<!DEBUG_INFO_SMARTCAST!>i<!>))
        foo((<!REDUNDANT_LABEL_WARNING!>l3@<!> <!DEBUG_INFO_SMARTCAST!>i<!>))
    }

    val a: Int = <!REDUNDANT_LABEL_WARNING!>l4@<!> <!TYPE_MISMATCH!>""<!>
    val b: Int = (<!TYPE_MISMATCH!>""<!>)
    val c: Int = checkSubtype<Int>(<!TYPE_MISMATCH!>""<!>)
    val d: Int = <!TYPE_MISMATCH, TYPE_MISMATCH, TYPE_MISMATCH!>checkSubtype<Long>(<!TYPE_MISMATCH!>""<!>)<!>


    foo(<!REDUNDANT_LABEL_WARNING!>l4@<!> <!TYPE_MISMATCH!>""<!>)
    foo((<!TYPE_MISMATCH!>""<!>))
    foo(checkSubtype<Int>(<!TYPE_MISMATCH!>""<!>))
    foo(<!TYPE_MISMATCH!>checkSubtype<Long>(<!TYPE_MISMATCH!>""<!>)<!>)

    use(a, b, c, d)
}

fun foo(i: Int) = i

fun use(vararg a: Any?) = a

/* GENERATED_FIR_TAGS: classDeclaration, equalityExpression, funWithExtensionReceiver, functionDeclaration,
functionalType, ifExpression, infix, localProperty, nullableType, outProjection, propertyDeclaration, smartcast,
stringLiteral, typeParameter, typeWithExtension, vararg */
