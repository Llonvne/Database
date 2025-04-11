package cn.llonvne.db.clause

import cn.llonvne.db.table.TableSpecification
import cn.llonvne.db.table.TableSpecification.ColumnsSpecification.ColumnModifier.*

sealed interface ColumnClause<T : Any, E : Any> : Clause {
    val column: TableSpecification.ColumnsSpecification<T, E>

    fun resolveColumnName(): String {
        val names =
            column.modifiers.filterIsInstance<ColumnName<E>>()
        return when (names.size) {
            0 -> column.property.name
            1 -> names.first().name
            else -> throw IllegalArgumentException("多于一个列名")
        }
    }

    fun resolveModifiers(): String {
        return column.modifiers.mapNotNull {
            when (it) {
                is ColumnName<*> -> null
                is Nullable<E> -> resolveNullable(it)
                is PrimaryKey<E> -> resolvePrimaryKey(it)
                is Unique<E> -> resolveUnique(it)
                is DefaultValue<E> -> resolveDefault(it)
            }
        }.joinToString("\n")
    }

    private fun resolveDefault(default: DefaultValue<E>): String {
        return "default ${value(default.default)}"
    }

    fun value(e: E): String

    private fun resolvePrimaryKey(primaryKey: PrimaryKey<E>): String {
        return "constraint ${resolveColumnName()}_pk primary key"
    }

    private fun resolveNullable(nullable: Nullable<E>): String {
        if (nullable.nullable) {
            return ""
        } else {
            return "not null"
        }
    }

    private fun resolveUnique(unique: Unique<E>): String {
        return "constraint ${unique.name.notBlankOr(resolveColumnName() + "_unique")} unique"
    }

    private fun String.notBlankOr(default: String): String {
        if (isBlank()) {
            return default
        } else {
            return this
        }
    }
}