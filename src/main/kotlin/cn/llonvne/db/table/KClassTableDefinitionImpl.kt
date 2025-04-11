package cn.llonvne.db.table

import cn.llonvne.db.database.DatabaseSpecification
import kotlin.reflect.KClass

@PublishedApi
internal class KClassTableDefinitionImpl<T : Any>(
    val databaseSpecification: DatabaseSpecification,
    override val entityKClass: KClass<T>,
    specifications: List<TableSpecification<T>>,
) : TableDefinition<T> {
    private val context = TableContext(entityKClass, specifications)

    override fun ddl(): String = context.createTableClause(databaseSpecification).toString()

    override fun context(): TableContext<T> {
        return context
    }
}