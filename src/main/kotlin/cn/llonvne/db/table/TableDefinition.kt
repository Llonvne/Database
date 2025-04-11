package cn.llonvne.db.table

import kotlin.reflect.KClass


interface TableDefinition<T : Any> {
    val entityKClass: KClass<T>

    fun ddl(): String

    fun context(): TableContext<T>
}




