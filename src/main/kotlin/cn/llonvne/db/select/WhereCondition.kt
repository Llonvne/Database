package cn.llonvne.db.select

import kotlin.reflect.KProperty1

sealed interface WhereCondition {
    data class AndCondition(val left: WhereCondition, val right: WhereCondition) : WhereCondition
    data class OrCondition(val left: WhereCondition, val right: WhereCondition) : WhereCondition
    data class NotCondition(val condition: WhereCondition) : WhereCondition

    data class ColumnEq<T : Any, E : Any>(val column: KProperty1<T, E>, val value: () -> E) : WhereCondition
}