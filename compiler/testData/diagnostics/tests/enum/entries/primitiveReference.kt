// RUN_PIPELINE_TILL: BACKEND
// LANGUAGE: +EnumEntries
// WITH_STDLIB

enum class Some {}

val x = Some::<!DEPRECATED_ACCESS_TO_ENUM_ENTRY_PROPERTY_AS_REFERENCE!>entries<!>

/* GENERATED_FIR_TAGS: callableReference, enumDeclaration, propertyDeclaration */
