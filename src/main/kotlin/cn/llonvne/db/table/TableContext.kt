package cn.llonvne.db.table

import cn.llonvne.db.clause.ColumnClause
import cn.llonvne.db.clause.LongColumnClause
import cn.llonvne.db.clause.TableClause
import cn.llonvne.db.clause.VarCharColumnClause
import cn.llonvne.db.database.DatabaseSpecification
import cn.llonvne.db.foreignkey.ForeignKeyClause
import cn.llonvne.db.foreignkey.ForeignKeyContext
import cn.llonvne.db.foreignkey.ForeignKeySpecification
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

class TableContext<T : Any>(
    private val kcls: KClass<T>,
    private val specification: List<TableSpecification<T>>,
) : ForeignKeyContext {

    override fun resolveLocalColumnName(kProperty1: KProperty1<*, *>): String {
        return findColumnClause(kProperty1).resolveColumnName()
    }

    fun resolveTableName(): String {
        val tablename = specification.filterIsInstance<TableName<T>>()
        return when (tablename.size) {
            0 -> kcls.simpleName ?: throw IllegalArgumentException("表名无法确定")
            1 -> tablename.first().name
            else -> throw IllegalArgumentException("多个表名提供")
        }
    }

    fun resolveColumns(): List<ColumnClause<T, *>> {
        val columns = specification.filterIsInstance<TableSpecification.ColumnsSpecification<T, *>>()
        val clauses =
            columns.map {
                when (it) {
                    is TableSpecification.ColumnsSpecification.LongColumn<T> -> resolveLongColumn(it)
                    is TableSpecification.ColumnsSpecification.VarcharColumn<T, *> -> VarCharColumnClause(it)
                }
            }
        return clauses
    }

    fun findColumnClause(property1: KProperty1<*, *>): ColumnClause<T, *> {
        val columnClauses = resolveColumns().filter {
            it.column.property == property1
        }
        return when (columnClauses.size) {
            0 -> throw IllegalStateException("${property1.name} not found")
            1 -> columnClauses.first()
            else -> throw IllegalStateException("too much ${property1.name}")
        }
    }

    fun resolveLongColumn(longColumn: TableSpecification.ColumnsSpecification.LongColumn<T>): LongColumnClause<T> =
        LongColumnClause(longColumn)

    fun resolveForeignKeys(): List<ForeignKeyClause<T>> {
        val keys = specification.filterIsInstance<TableSpecification.ForeignKey<T>>()
        return keys.map { it.foreignKeySpecification }
            .map {
                when (it) {
                    is ForeignKeySpecification.ForeignKey<*, *, *> -> ForeignKeyClause(
                        this,
                        it as ForeignKeySpecification.ForeignKey<T, *, *>
                    )
                }
            }
    }


    fun createTableClause(
        databaseSpecification: DatabaseSpecification
    ): TableClause<T> =
        TableClause(databaseSpecification.schema(), resolveTableName(), resolveColumns() + resolveForeignKeys())
}







