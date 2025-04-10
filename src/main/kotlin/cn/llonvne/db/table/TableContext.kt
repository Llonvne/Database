package cn.llonvne.db.table

import cn.llonvne.db.table.TableSpecification.ColumnsSpecification.ColumnModifier
import kotlin.reflect.KClass

class TableContext<T : Any>(
    private val kcls: KClass<T>,
    private val specification: List<TableSpecification<T>>
) {
    private fun resolveTableName(): String {
        val tablename = specification.filterIsInstance<TableName<T>>()
        return when (tablename.size) {
            0 -> kcls.qualifiedName ?: throw IllegalArgumentException("表名无法确定")
            1 -> tablename.first().name
            else -> throw IllegalArgumentException("多个表名提供")
        }
    }

    fun resolveColumns() {
        val columns = specification.filterIsInstance<TableSpecification.ColumnsSpecification<T, *>>()
        columns.map {
            when (it) {
                is TableSpecification.ColumnsSpecification.LongColumn<T> -> resolveLongColumn(it)
                is TableSpecification.ColumnsSpecification.VarcharColumn<T, *> -> TODO()
            }
        }
    }

    fun resolveLongColumn(longColumn: TableSpecification.ColumnsSpecification.LongColumn<T>): LongColumnClause<T> {
        return LongColumnClause(longColumn)
    }

    fun createTableClause(): TableClause {
        return TableClause(resolveTableName(), emptyList())
    }
}

sealed interface Clause

sealed interface ColumnClause<T : Any, E : Any> {

    val column: TableSpecification.ColumnsSpecification<T, E>

    fun resolveColumnName() {
        val names = column.modifiers.filterIsInstance<ColumnModifier.ColumnName<E>>()
        when (names.size) {
            0 -> column.property.name
            1 -> names.first().name
            else -> throw IllegalArgumentException("多于一个列名")
        }
    }
}

data class LongColumnClause<T : Any>(
    override val column: TableSpecification.ColumnsSpecification<T, Long>,
) : ColumnClause<T, Long> {

    override fun toString(): String {
        return """${resolveColumnName()} varchar(255)"""
    }
}

data class TableClause(
    val tableName: String,
    val clauses: List<Clause>
) : Clause {
    override fun toString(): String {
        return """create table "$tableName"
(
${clauses.joinToString(",")}
);"""
    }
}