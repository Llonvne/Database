package cn.llonvne.db.table

import java.awt.desktop.QuitStrategy
import java.nio.file.WatchEvent
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty1

interface TableDefinition<E : Any> {
    val entityKClass: KClass<E>

    companion object {
        internal inline fun <reified E : Any> define(vararg specification: TableSpecification<E>): TableDefinition<E> =
            KClassTableDefinitionImpl(E::class, specification.toList())
    }

    fun ddl(): String
}


sealed interface TableSpecification<T : Any> {
    data class EmptyConstructor<T : Any>(
        val constructor: () -> T,
    ) : TableSpecification<T>

    data class TableName<T : Any>(val name: String) : TableSpecification<T>

    sealed interface ColumnsSpecification<T : Any, E : Any> : TableSpecification<T> {
        val property: KProperty1<T, E>
        val modifiers: List<ColumnModifier<E>>

        sealed interface ColumnModifier<E> {

            data class ColumnName<E>(val name: String) : ColumnModifier<E>

            data class Unique<E : Any>(val name: String = "") : ColumnModifier<E>

            data class Nullable<E : Any>(val nullable: Boolean) : ColumnModifier<E>

            data class PrimaryKey<E : Any>(val strategy: PrimaryKeyStrategy = PrimaryKeyStrategy.Single) :
                ColumnModifier<E>

            enum class PrimaryKeyStrategy {
                Single
            }
        }

        data class VarcharColumn<T : Any, E : Any>(
            val size: Int,
            override val property: KProperty1<T, E>,
            override val modifiers: List<ColumnModifier<E>> = emptyList()
        ) : ColumnsSpecification<T, E>

        data class LongColumn<T : Any>(
            override val property: KProperty1<T, Long>,
            override val modifiers: List<ColumnModifier<Long>> = emptyList()
        ) : ColumnsSpecification<T, Long>
    }
}

fun <T : Any, E : Any> KProperty1<T, E>.varchar(
    size: Int,
    vararg modifiers: TableSpecification.ColumnsSpecification.ColumnModifier<E>
) =
    TableSpecification.ColumnsSpecification.VarcharColumn(size, this, modifiers.toList())

fun <T : Any> KProperty1<T, Long>.long(
    vararg modifiers: TableSpecification.ColumnsSpecification.ColumnModifier<Long>
) =
    TableSpecification.ColumnsSpecification.LongColumn(this, modifiers.toList())

@PublishedApi
internal class KClassTableDefinitionImpl<E : Any>(
    override val entityKClass: KClass<E>,
    val specifications: List<TableSpecification<E>>
) : TableDefinition<E> {

    private val context = TableContext(entityKClass, specifications)

    override fun ddl(): String {
//        val tablename = context.resolveTableName()
//        return """CREATE TABLE \"$tablename\" """
        TODO()
    }
}
