package org.jetbrains.kotlinx.dataframe.plugin.impl.api

import org.jetbrains.kotlin.fir.types.ConeKotlinType
import org.jetbrains.kotlin.fir.types.ConeNullability
import org.jetbrains.kotlin.fir.types.classId
import org.jetbrains.kotlin.fir.types.isNullable
import org.jetbrains.kotlinx.dataframe.plugin.impl.AbstractInterpreter
import org.jetbrains.kotlinx.dataframe.plugin.impl.Arguments
import org.jetbrains.kotlinx.dataframe.plugin.impl.Present
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.annotations.*
import org.jetbrains.kotlinx.dataframe.api.Infer
import org.jetbrains.kotlinx.dataframe.api.pathOf
import org.jetbrains.kotlinx.dataframe.api.toPath
import org.jetbrains.kotlinx.dataframe.impl.api.GenericColumn
import org.jetbrains.kotlinx.dataframe.impl.api.GenericColumnsToInsert
import org.jetbrains.kotlinx.dataframe.impl.api.DataFrameLikeContainer
import org.jetbrains.kotlinx.dataframe.impl.api.GenericColumnGroup
import org.jetbrains.kotlinx.dataframe.impl.api.insertImplGenericContainer
import org.jetbrains.kotlinx.dataframe.plugin.extensions.KotlinTypeFacade
import org.jetbrains.kotlinx.dataframe.plugin.extensions.wrap
import org.jetbrains.kotlinx.dataframe.plugin.impl.data.ColumnAccessorApproximation
import org.jetbrains.kotlinx.dataframe.plugin.impl.data.ColumnPathApproximation
import org.jetbrains.kotlinx.dataframe.plugin.impl.data.ColumnWithPathApproximation
import org.jetbrains.kotlinx.dataframe.plugin.impl.data.InsertClauseApproximation
import org.jetbrains.kotlinx.dataframe.plugin.impl.data.KPropertyApproximation
import org.jetbrains.kotlinx.dataframe.plugin.impl.columnAccessor
import org.jetbrains.kotlinx.dataframe.plugin.impl.columnPath
import org.jetbrains.kotlinx.dataframe.plugin.impl.columnWithPath
import org.jetbrains.kotlinx.dataframe.plugin.impl.dataColumn
import org.jetbrains.kotlinx.dataframe.plugin.impl.dataFrame
import org.jetbrains.kotlinx.dataframe.plugin.impl.enum
import org.jetbrains.kotlinx.dataframe.plugin.impl.insertClause
import org.jetbrains.kotlinx.dataframe.plugin.impl.kproperty
import org.jetbrains.kotlinx.dataframe.plugin.impl.string
import org.jetbrains.kotlinx.dataframe.plugin.impl.type
import org.jetbrains.kotlinx.dataframe.plugin.pluginDataFrameSchema
import org.jetbrains.kotlinx.dataframe.plugin.utils.Names

/**
 * @see DataFrame.insert
 */
internal class Insert0 : AbstractInterpreter<InsertClauseApproximation>() {
    val Arguments.column: SimpleCol by dataColumn()
    val Arguments.receiver: PluginDataFrameSchema by dataFrame()

    override fun Arguments.interpret(): InsertClauseApproximation {
        return InsertClauseApproximation(receiver, column)
    }
}

internal class Insert1 : AbstractInterpreter<InsertClauseApproximation>() {
    val Arguments.name: String by string()
    val Arguments.infer: Infer by enum(defaultValue = Present(Infer.Nulls))
    val Arguments.expression: TypeApproximation by type()
    val Arguments.receiver: PluginDataFrameSchema by dataFrame()

    override fun Arguments.interpret(): InsertClauseApproximation {
        return InsertClauseApproximation(receiver, SimpleCol(name, expression))
    }
}

internal class Insert2 : AbstractInterpreter<InsertClauseApproximation>() {
    val Arguments.column: ColumnAccessorApproximation by columnAccessor()
    val Arguments.infer: Infer by enum(defaultValue = Present(Infer.Nulls))
    val Arguments.expression: TypeApproximation by type()
    val Arguments.receiver: PluginDataFrameSchema by dataFrame()

    override fun Arguments.interpret(): InsertClauseApproximation {
        return InsertClauseApproximation(receiver, SimpleCol(column.name, expression))
    }
}

internal class Insert3 : AbstractInterpreter<InsertClauseApproximation>() {
    val Arguments.column: KPropertyApproximation by kproperty()
    val Arguments.infer: Infer by enum(defaultValue = Present(Infer.Nulls))
    val Arguments.expression: TypeApproximation by type()
    val Arguments.receiver: PluginDataFrameSchema by dataFrame()

    override fun Arguments.interpret(): InsertClauseApproximation {
        return InsertClauseApproximation(receiver, SimpleCol(column.name, expression))
    }
}

internal class Under0 : AbstractInterpreter<PluginDataFrameSchema>() {
    val Arguments.column: ColumnWithPathApproximation by columnWithPath()
    val Arguments.receiver: InsertClauseApproximation by insertClause()

    override fun Arguments.interpret(): PluginDataFrameSchema {
        return receiver.df.insertImpl(listOf(GenericColumnsToInsert(column.path.path.toPath(), receiver.column)), anyDataFrame)
    }
}

internal class Under1 : AbstractInterpreter<PluginDataFrameSchema>() {
    val Arguments.columnPath: ColumnPathApproximation by columnPath()
    val Arguments.receiver: InsertClauseApproximation by insertClause()

    override fun Arguments.interpret(): PluginDataFrameSchema {
        return receiver.df.insertImpl(listOf(GenericColumnsToInsert(columnPath.path.toPath(), receiver.column)), anyRow)
    }
}

internal class Under2 : AbstractInterpreter<PluginDataFrameSchema>() {
    val Arguments.column: ColumnAccessorApproximation by columnAccessor()
    val Arguments.receiver: InsertClauseApproximation by insertClause()

    override fun Arguments.interpret(): PluginDataFrameSchema {
        return receiver.df.insertImpl(listOf(GenericColumnsToInsert(pathOf(column.name), receiver.column)), anyRow)
    }
}

internal class Under3 : AbstractInterpreter<PluginDataFrameSchema>() {
    val Arguments.column: KPropertyApproximation by kproperty()
    val Arguments.receiver: InsertClauseApproximation by insertClause()

    override fun Arguments.interpret(): PluginDataFrameSchema {
        return receiver.df.insertImpl(listOf(GenericColumnsToInsert(pathOf(column.name), receiver.column)), anyRow)
    }
}

internal class Under4 : AbstractInterpreter<PluginDataFrameSchema>() {
    val Arguments.column: String by string()
    val Arguments.receiver: InsertClauseApproximation by insertClause()

    override fun Arguments.interpret(): PluginDataFrameSchema {
        return receiver.df.insertImpl(listOf(GenericColumnsToInsert(pathOf(column), receiver.column)), anyRow)
    }
}

data class PluginDataFrameSchema(
    private val columns: List<SimpleCol>
) : DataFrameLikeContainer<SimpleCol> {
    override fun columns(): List<SimpleCol> {
        return columns
    }

    override fun toString(): String {
        return columns.asString()
    }
}

private fun List<SimpleCol>.asString(indent: String = ""): String {
    return joinToString("\n") {
        val col = when (it) {
            is SimpleFrameColumn -> {
                "${it.name}*\n" + it.columns().asString("$indent   ")
            }
            is SimpleColumnGroup -> {
                "${it.name}\n" + it.columns().asString("$indent   ")
            }
            is SimpleCol -> {
//                val type = (it.type as TypeApproximationImpl).let {
//                    val nullability = if (it.nullable) "?" else ""
//                    "${it.fqName}$nullability"
//                }
                "${it.name}: ${it.type}"
            }
            else -> TODO()
        }
        "$indent$col"
    }
}

open class SimpleCol(
    val name: String,
    open val type: TypeApproximation
) : GenericColumn {

    override fun name(): String {
        return name
    }

    open fun rename(s: String): SimpleCol {
        return SimpleCol(s, type)
    }

    open fun changeType(type: TypeApproximation): SimpleCol {
        return SimpleCol(name, type)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SimpleCol

        if (name != other.name) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + type.hashCode()
        return result
    }

    override fun toString(): String {
        return "SimpleCol(name='$name', type=$type)"
    }

    open fun kind(): SimpleColumnKind {
        return SimpleColumnKind.VALUE
    }
}

enum class SimpleColumnKind {
    VALUE, GROUP, FRAME
}

data class SimpleFrameColumn(
    private val name1: String,
    private val columns: List<SimpleCol>,
    // probably shouldn't be called at all?
    // exists only because SimpleCol has it
    // but in fact it's for `materialize` to decide what should be the type of the property / accessors
    val anyFrameType: TypeApproximation,
) : GenericColumnGroup<SimpleCol>, SimpleCol(name1, anyFrameType) {
    override fun columns(): List<SimpleCol> {
        return columns
    }

    override fun rename(s: String): SimpleFrameColumn {
        return SimpleFrameColumn(name1, columns, anyFrameType)
    }

    override fun kind(): SimpleColumnKind {
        return SimpleColumnKind.FRAME
    }
}

class SimpleColumnGroup(
    name: String,
    private val columns: List<SimpleCol>,
    columnGroupType: TypeApproximation
) : GenericColumnGroup<SimpleCol>, SimpleCol(name, columnGroupType) {

    override fun columns(): List<SimpleCol> {
        return columns
    }

    override fun rename(s: String): SimpleColumnGroup {
        return SimpleColumnGroup(s, columns, type)
    }

    override fun changeType(type: TypeApproximation): SimpleCol {
        return TODO()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as SimpleColumnGroup

        if (name != other.name) return false
        if (columns != other.columns) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + columns.hashCode()
        return result
    }

    override fun kind(): SimpleColumnKind {
        return SimpleColumnKind.GROUP
    }
}

fun KotlinTypeFacade.simpleColumnOf(name: String, type: ConeKotlinType): SimpleCol {
    return if (type.classId == Names.DATA_ROW_CLASS_ID) {
        val schema = pluginDataFrameSchema(type)
        val group = SimpleColumnGroup(name, schema.columns(), anyRow)
        val column = if (type.isNullable) {
            makeNullable(group)
        } else {
            group
        }
        column
    } else if (type.classId == Names.DF_CLASS_ID && type.nullability == ConeNullability.NOT_NULL) {
        val schema = pluginDataFrameSchema(type)
        SimpleFrameColumn(name, schema.columns(), anyDataFrame)
    } else {
        SimpleCol(name, type.wrap())
    }
}

private fun KotlinTypeFacade.makeNullable(column: SimpleCol): SimpleCol {
    return when (column) {
        is SimpleColumnGroup -> {
            SimpleColumnGroup(column.name, column.columns().map { makeNullable(column) }, anyRow)
        }
        is SimpleFrameColumn -> column
        else -> SimpleCol(column.name, column.type.changeNullability { true })
    }
}

@PublishedApi
internal fun PluginDataFrameSchema.insertImpl(
    columns: List<GenericColumnsToInsert<SimpleCol>>,
    columnGroupType: TypeApproximation
): PluginDataFrameSchema {
    return insertImplGenericContainer<PluginDataFrameSchema, SimpleCol, GenericColumnGroup<SimpleCol>>(
        this,
        columns,
        columns.firstOrNull()?.referenceNode?.getRoot(),
        0,
        factory = { PluginDataFrameSchema(it) },
        empty = PluginDataFrameSchema(emptyList()),
        rename = { rename(it) },
        createColumnGroup = { name, columns ->
            SimpleColumnGroup(name, columns, columnGroupType)
        }
    )
}
