// RUN_PIPELINE_TILL: BACKEND
class Array<E>(e: E) {
    val k = Array(1) {
        1 <!USELESS_CAST!>as Any<!>
        e <!USELESS_CAST!>as Any?<!>
    }
}
