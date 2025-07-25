// RUN_PIPELINE_TILL: FRONTEND
// CHECK_TYPE
// LANGUAGE: +ProhibitAssigningSingleElementsToVarargsInNamedForm +AllowAssigningArrayElementsToVarargsInNamedFormForFunctions

fun <T> array1(vararg a : T) = a

fun main() {
    val a = array1("a", "b")
    val b = array1(1, 1)
    join(1)
    join(1, "2")
    join(1, "2", "3")
    join(<!NON_VARARG_SPREAD!>*<!>1, "2")
    join(1, *<!ARGUMENT_TYPE_MISMATCH!>"2"<!>)
    join(x = 1, a = <!ARGUMENT_TYPE_MISMATCH, ASSIGNING_SINGLE_ELEMENT_TO_VARARG_IN_NAMED_FORM_FUNCTION_ERROR!>"2"<!>)
    join(x = <!NON_VARARG_SPREAD!>*<!>1, a = *<!ARGUMENT_TYPE_MISMATCH!>"2"<!>)
    join(x = 1, a = a)
    join(x = 1, a = <!ARGUMENT_TYPE_MISMATCH!>b<!>)
    join(1, *a)
    join(1, *<!ARGUMENT_TYPE_MISMATCH!>b<!>)
    join(1, *a, "3")
    join(1, *<!ARGUMENT_TYPE_MISMATCH!>b<!>, "3")
    join(1, "4", *a, "3")
    join(1, "4", *<!ARGUMENT_TYPE_MISMATCH!>b<!>, "3")
    join(1, "4", *a)
    join(1, "4", *a, *a, "3")
    join(1, "4", *a, *<!ARGUMENT_TYPE_MISMATCH!>b<!>, "3")
    join(a = a, x = 1)
    join(a = <!ARGUMENT_TYPE_MISMATCH!>b<!>, x = 1)
    join(a = a, x = 1)

    joinG<String>(1, "2")
    joinG<String>(<!NON_VARARG_SPREAD!>*<!>1, "2")
    joinG<String>(1, *<!ARGUMENT_TYPE_MISMATCH!>"2"<!>)
    joinG<String>(x = 1, a = a)
    joinG<String>(x = 1, a = <!ARGUMENT_TYPE_MISMATCH, ASSIGNING_SINGLE_ELEMENT_TO_VARARG_IN_NAMED_FORM_FUNCTION_ERROR!>"2"<!>)
    joinG<String>(x = <!NON_VARARG_SPREAD!>*<!>1, a = *<!ARGUMENT_TYPE_MISMATCH!>"2"<!>)
    joinG<String>(1, *a)
    joinG<String>(1, *a, "3")
    joinG<String>(1, "4", *a, "3")
    joinG<String>(1, "4", *a)
    joinG<String>(1, "4", *a, *a, "3")
    joinG<String>(a = a, x = 1)

    joinG(1, "2")
    joinG(<!NON_VARARG_SPREAD!>*<!>1, "2")
    <!CANNOT_INFER_PARAMETER_TYPE!>joinG<!>(1, *<!ARGUMENT_TYPE_MISMATCH!>"2"<!>)
    joinG(x = 1, a = a)
    <!CANNOT_INFER_PARAMETER_TYPE!>joinG<!>(x = 1, a = <!ARGUMENT_TYPE_MISMATCH, ASSIGNING_SINGLE_ELEMENT_TO_VARARG_IN_NAMED_FORM_FUNCTION_ERROR!>"2"<!>)
    <!CANNOT_INFER_PARAMETER_TYPE!>joinG<!>(x = <!NON_VARARG_SPREAD!>*<!>1, a = *<!ARGUMENT_TYPE_MISMATCH!>"2"<!>)
    joinG(1, *a)
    joinG(1, *a, "3")
    joinG(1, "4", *a, "3")
    joinG(1, "4", *a)
    joinG(1, "4", *a, *a, "3")
    joinG(a = a, x = 1)

    val x1 = joinT(1, "2")
    checkSubtype<String?>(x1)
    val x2 = joinT(<!NON_VARARG_SPREAD!>*<!>1, "2")
    checkSubtype<String?>(x2)
    val x6 = joinT(1, *a)
    checkSubtype<String?>(x6)
    val x7 = joinT(1, *a, "3")
    checkSubtype<String?>(x7)
    val x8 = joinT(1, "4", *a, "3")
    checkSubtype<String?>(x8)
    val x9 = joinT(1, "4", *a)
    checkSubtype<String?>(x9)
    val x10 = joinT(1, "4", *a, *a, "3")
    checkSubtype<String?>(x10)
    val x11 = joinT(a = a, x = 1)
    checkSubtype<String?>(x11)
    val x12 = joinT(x = 1, a = a)
    checkSubtype<String?>(x12)
}

fun join(x : Int, vararg a : String) : String {
    val b = StringBuilder(x.toString())
    for (s in a) {
        b.append(s)
    }
    return b.toString()
}

fun <T> joinG(x : Int, vararg a : T) : String {
    val b = StringBuilder(x.toString())
    for (s in a) {
        b.append(s)
    }
    return b.toString()
}

fun <T: Any> joinT(x : Int, vararg a : T) : T? {
    return null
}

/* GENERATED_FIR_TAGS: capturedType, classDeclaration, flexibleType, forLoop, funWithExtensionReceiver,
functionDeclaration, functionalType, infix, integerLiteral, javaFunction, localProperty, nullableType, outProjection,
propertyDeclaration, stringLiteral, typeConstraint, typeParameter, typeWithExtension, vararg */
