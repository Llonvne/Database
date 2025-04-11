package cn.llonvne.db.select

import cn.llonvne.db.database.DatabaseSpecification
import cn.llonvne.db.table.TableDefinition
import kotlin.jvm.internal.PropertyReference1Impl
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

class SelectContext(
    private val selectSpecification: List<SelectSpecification>
) {
    private val fromClause = resolveFromClause()

    private val aliasRegistry: MutableSet<String> = mutableSetOf()

    private fun resolveFieldsClause(): FieldsClause {
        val fields = selectSpecification.filterIsInstance<SelectSpecification.Fields>()
        assert(fields.size == 1)
        return FieldsClause(this, fields.first())
    }

    private fun resolveFromClause(): FromClause {
        val from = selectSpecification.filterIsInstance<SelectSpecification.From>()
        assert(from.size == 1)
        return FromClause(this, from.first())
    }

    fun registerAlias(alias: String): Boolean {
        if (alias !in aliasRegistry) {
            aliasRegistry.add(alias)
            return true
        } else {
            return false
        }
    }

    fun getTableAlias(tableDefinition: TableDefinition<*>): String {
        return fromClause.getRegisteredTableAlias(tableDefinition)
    }

    fun resolveTableFromProperty(kProperty1: KProperty1<*, *>): TableDefinition<*> {
        kProperty1 as PropertyReference1Impl
        // 获取属性声明所在的类
        val ownerClass = (kProperty1.owner as? KClass<*>)?.java
            ?: throw IllegalArgumentException("Property owner must be a class reference")

        // 在FROM子句的所有表定义中查找匹配的实体类
        return fromClause.resolveTableDefinition().firstOrNull { tableDef ->
            tableDef.entityKClass.qualifiedName == ownerClass.canonicalName
        } ?: throw NoSuchElementException("No table found for property ${kProperty1.name}")
    }

    fun createSelectClause(databaseSpecification: DatabaseSpecification): SelectClause {
        return SelectClause(fromClause, resolveFieldsClause())
    }

    fun <T : Any> resolveTablename(tableDefinition: TableDefinition<T>): String {
        return tableDefinition.context().resolveTableName()
    }

    fun resolveColumnName(property: KProperty1<*, *>): String {
        val tableContext = resolveTableFromProperty(property).context()
        val columnClause = tableContext.findColumnClause(property)
        val columnName = columnClause.resolveColumnName()
        return columnName
    }
}