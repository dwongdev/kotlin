// RUN_PIPELINE_TILL: BACKEND
class Foo {
    fun bar(arg: Bar) {
        arg.foo()
    }
}

fun Bar.foo() {}

class Bar {
    fun Foo.foo() {}

    fun bar(arg: Foo) {
        arg.foo()
    }
}

/* GENERATED_FIR_TAGS: classDeclaration, funWithExtensionReceiver, functionDeclaration */
