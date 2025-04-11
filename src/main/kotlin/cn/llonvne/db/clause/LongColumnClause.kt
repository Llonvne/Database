package cn.llonvne.db.clause

import cn.llonvne.db.table.TableSpecification

data class LongColumnClause<T : Any>(
    override val column: TableSpecification.ColumnsSpecification<T, Long>,
) : ColumnClause<T, Long> {
    override fun toString(): String = """${resolveColumnName()} integer
        |${resolveModifiers()}""".trimMargin()

    override fun value(e: Long): String {
        return e.toString()
    }
}