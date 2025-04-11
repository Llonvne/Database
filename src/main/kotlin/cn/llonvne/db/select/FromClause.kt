package cn.llonvne.db.select

import cn.llonvne.db.clause.Clause
import cn.llonvne.db.table.TableDefinition
import kotlin.random.Random

class FromClause(
    private val selectContext: SelectContext,
    private val from: SelectSpecification.From
) : Clause {

    private val tableDefinitionAliasMap: MutableMap<TableDefinition<*>, String> = mutableMapOf()

    fun resolveTableDefinition(): List<TableDefinition<*>> {
        return from.tableDefinition
    }

    fun getRegisteredTableAlias(tableDefinition: TableDefinition<*>): String {

        val fp = tableDefinitionAliasMap[tableDefinition]
        if (fp != null) {
            return fp
        }

        var alias = selectContext.resolveTablename(tableDefinition)
        while (!selectContext.registerAlias(alias)) {
            alias += Random.nextInt()
        }
        tableDefinitionAliasMap[tableDefinition] = alias
        return alias
    }

    fun getTableDefinitionFromAlias(alias: String): String? {
        return tableDefinitionAliasMap.entries.firstOrNull { it.value == alias }?.value
    }

    override fun toString(): String {
        return resolveTableDefinition().joinToString(",") {
            "${selectContext.resolveTablename(it)} as ${getRegisteredTableAlias(it)}"
        }
    }
}