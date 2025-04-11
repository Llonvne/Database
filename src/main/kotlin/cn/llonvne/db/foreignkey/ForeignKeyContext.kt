package cn.llonvne.db.foreignkey

import cn.llonvne.db.table.TableDefinition
import kotlin.reflect.KProperty1

interface ForeignKeyContext {
    fun resolveLocalColumnName(kProperty1: KProperty1<*, *>): String

    fun resolveForeignColumnName(tableDefinition: TableDefinition<*>, kProperty1: KProperty1<*, *>): String {
        return tableDefinition.context().findColumnClause(kProperty1).resolveColumnName()
    }
}