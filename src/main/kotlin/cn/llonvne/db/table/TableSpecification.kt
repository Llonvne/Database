package cn.llonvne.db.table

import cn.llonvne.db.foreignkey.ForeignKeySpecification
import kotlin.reflect.KProperty1

sealed interface TableSpecification<T : Any> {
    data class EmptyConstructor<T : Any>(
        val constructor: () -> T,
    ) : TableSpecification<T>

    data class TableName<T : Any>(
        val name: String,
    ) : TableSpecification<T>

    data class ForeignKey<T : Any>(val foreignKeySpecification: ForeignKeySpecification) : TableSpecification<T>

    sealed interface ColumnsSpecification<T : Any, E : Any> : TableSpecification<T> {
        val property: KProperty1<T, E>
        val modifiers: List<ColumnModifier<E>>

        sealed interface ColumnModifier<E> {
            data class ColumnName<E>(
                val name: String,
            ) : ColumnModifier<E>

            data class Unique<E : Any>(
                val name: String = "",
            ) : ColumnModifier<E>

            data class Nullable<E : Any>(
                val nullable: Boolean,
            ) : ColumnModifier<E>

            data class PrimaryKey<E : Any>(
                val strategy: PrimaryKeyStrategy = PrimaryKeyStrategy.Single,
            ) : ColumnModifier<E>

            enum class PrimaryKeyStrategy {
                Single,
            }

            data class DefaultValue<E : Any>(val default: E) : ColumnModifier<E>
        }

        data class VarcharColumn<T : Any, E : Any>(
            val size: Int,
            override val property: KProperty1<T, E>,
            override val modifiers: List<ColumnModifier<E>> = emptyList(),
        ) : ColumnsSpecification<T, E>

        data class LongColumn<T : Any>(
            override val property: KProperty1<T, Long>,
            override val modifiers: List<ColumnModifier<Long>> = emptyList(),
        ) : ColumnsSpecification<T, Long>
    }
}