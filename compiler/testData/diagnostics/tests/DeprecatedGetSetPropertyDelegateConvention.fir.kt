// RUN_PIPELINE_TILL: FRONTEND
// DIAGNOSTICS: -UNUSED_PARAMETER

import kotlin.reflect.KProperty

class CustomDelegate {
    operator fun get(thisRef: Any?, prop: KProperty<*>): String = prop.name
    operator fun set(thisRef: Any?, prop: KProperty<*>, value: String) {}
}

class OkDelegate {
    operator fun getValue(thisRef: Any?, prop: KProperty<*>): String = prop.name
    operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: String) {}
}

class CustomDelegate2 {
    operator fun get(thisRef: Any?, prop: KProperty<*>): String = prop.name
    operator fun set(thisRef: Any?, prop: KProperty<*>, value: String) {}

    operator fun getValue(thisRef: Any?, prop: KProperty<*>): Int = 5
    operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: Int) {}
}

class CustomDelegate3 {
    operator fun get(thisRef: Any?, prop: KProperty<*>): String = prop.name
    operator fun set(thisRef: Any?, prop: KProperty<*>, value: String) {}
}

operator fun OkDelegate.get(thisRef: Any?, prop: KProperty<*>): Int = 4
operator fun OkDelegate.set(thisRef: Any?, prop: KProperty<*>, value: Int) {}

operator fun CustomDelegate3.getValue(thisRef: Any?, prop: KProperty<*>): Int = 4
operator fun CustomDelegate3.setValue(thisRef: Any?, prop: KProperty<*>, value: Int) {}

class Example {

    var a <!DELEGATE_SPECIAL_FUNCTION_MISSING, DELEGATE_SPECIAL_FUNCTION_MISSING!>by<!> CustomDelegate()
    val aval <!DELEGATE_SPECIAL_FUNCTION_MISSING!>by<!> CustomDelegate()
    var b by OkDelegate()
    var c by CustomDelegate2()
    var d by CustomDelegate3()

    fun test() {
        requireString(<!ARGUMENT_TYPE_MISMATCH!>a<!>)
        requireString(<!ARGUMENT_TYPE_MISMATCH!>aval<!>)
        requireString(b)
        requireInt(c)
        requireInt(d)
    }

    fun requireString(s: String) {}
    fun requireInt(n: Int) {}

}

/* GENERATED_FIR_TAGS: classDeclaration, funWithExtensionReceiver, functionDeclaration, integerLiteral, nullableType,
operator, propertyDeclaration, propertyDelegate, setter, starProjection */
