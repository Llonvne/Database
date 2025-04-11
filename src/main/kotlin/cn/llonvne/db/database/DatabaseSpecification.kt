package cn.llonvne.db.database

import cn.llonvne.db.table.KClassTableDefinitionImpl
import cn.llonvne.db.table.TableDefinition
import cn.llonvne.db.table.TableSpecification
import kotlin.reflect.KClass

interface DatabaseSpecification {

    fun schema(): String

    fun <E : Any> define(entityKClass: KClass<E>, vararg specification: TableSpecification<E>): TableDefinition<E> =
        KClassTableDefinitionImpl(this, entityKClass, specification.toList())

    companion object {
        inline fun <reified E : Any> DatabaseSpecification.define(vararg specification: TableSpecification<E>) =
            define(E::class, *specification)
    }
}