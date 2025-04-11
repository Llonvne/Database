package cn.llonvne.db.clause

import cn.llonvne.db.table.TableSpecification

class VarCharColumnClause<T : Any, E : Any>(
    override val column: TableSpecification.ColumnsSpecification.VarcharColumn<T, E>
) : ColumnClause<T, E> {
    override fun toString(): String {
        return """${resolveColumnName()} varchar(${column.size})
            |${resolveModifiers()}""".trimMargin()
    }

    override fun value(e: E): String {
        return "\"$e\""
    }
}