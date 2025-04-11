package cn.llonvne.db.table

import cn.llonvne.db.foreignkey.ForeignKeySpecification
import kotlin.reflect.KProperty1

typealias PrimaryKey<E> = TableSpecification.ColumnsSpecification.ColumnModifier.PrimaryKey<E>

typealias PrimaryKeyStrategy = TableSpecification.ColumnsSpecification.ColumnModifier.PrimaryKeyStrategy

typealias TableName<T> = TableSpecification.TableName<T>

typealias UniqueConstraint<E> = TableSpecification.ColumnsSpecification.ColumnModifier.Unique<E>

typealias NullableColumn<E> = TableSpecification.ColumnsSpecification.ColumnModifier.Nullable<E>

fun <E : Any> NotNull() = NullableColumn<E>(false)

typealias ColumnName<E> = TableSpecification.ColumnsSpecification.ColumnModifier.ColumnName<E>

fun <T : Any, E : Any> KProperty1<T, E>.varchar(
    size: Int,
    vararg modifiers: TableSpecification.ColumnsSpecification.ColumnModifier<E>,
) = TableSpecification.ColumnsSpecification.VarcharColumn(size, this, modifiers.toList())

fun <T : Any> KProperty1<T, Long>.long(vararg modifiers: TableSpecification.ColumnsSpecification.ColumnModifier<Long>) =
    TableSpecification.ColumnsSpecification.LongColumn(this, modifiers.toList())

fun <T : Any, F : Any, E> KProperty1<T, E>.foreignKey(
    table: TableDefinition<F>,
    foreign: KProperty1<F, E>,
    cascadeSpecification: ForeignKeySpecification.CascadeSpecification? = null
): TableSpecification.ForeignKey<T> {
    return TableSpecification.ForeignKey<T>(
        ForeignKeySpecification.ForeignKey(
            this,
            foreign,
            table,
            cascadeSpecification
        )
    )
}