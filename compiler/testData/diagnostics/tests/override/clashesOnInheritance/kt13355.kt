// RUN_PIPELINE_TILL: FRONTEND
// FIR_IDENTICAL
interface IFooAny {
    val foo: Any
}

interface IFooStr : IFooAny {
    override val foo: String
}

abstract class BaseAny(override val foo: Any): IFooAny

abstract <!PROPERTY_TYPE_MISMATCH_ON_INHERITANCE!>class BaseStr<!> : BaseAny(42), IFooStr

class C : BaseStr()

/* GENERATED_FIR_TAGS: classDeclaration, integerLiteral, interfaceDeclaration, override, primaryConstructor,
propertyDeclaration */
