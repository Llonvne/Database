package cn.llonvne.db.select

import cn.llonvne.db.database.DatabaseSpecification
import cn.llonvne.db.table.TableDefinition
import kotlin.reflect.KProperty1

class WhereDsl<T : Any> {
    fun <E : Any> KProperty1<T, E>.eq(getter: () -> E): WhereCondition.ColumnEq<T, E> {
        return WhereCondition.ColumnEq(this, getter)
    }

    infix fun WhereCondition.and(other: WhereCondition): WhereCondition.AndCondition {
        return WhereCondition.AndCondition(this, other)
    }

    infix fun WhereCondition.or(other: WhereCondition) = WhereCondition.OrCondition(this, other)

    operator fun WhereCondition.not() = WhereCondition.NotCondition(this)
}

fun <T : Any> where(
    action: WhereDsl<T>.() -> WhereCondition
): SelectSpecification.Where {
    return SelectSpecification.Where(WhereDsl<T>().action())
}

fun <T : Any> fields(vararg fields: KProperty1<T, *>) = SelectSpecification.Fields(fields.toList())

fun <S : DatabaseSpecification> S.select(
    vararg selectSpecification: SelectSpecification
): String {
    val context = SelectContext(selectSpecification.toList())
    return context.createSelectClause(this).toString()
}

fun from(vararg define: TableDefinition<*>) = SelectSpecification.From(define.toList())