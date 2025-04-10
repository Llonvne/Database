package cn.llonvne.db.table

typealias PrimaryKey<E> = TableSpecification.ColumnsSpecification.ColumnModifier.PrimaryKey<E>

typealias PrimaryKeyStrategy = TableSpecification.ColumnsSpecification.ColumnModifier.PrimaryKeyStrategy

typealias TableName<T> = TableSpecification.TableName<T>

typealias UniqueConstraint<E> = TableSpecification.ColumnsSpecification.ColumnModifier.Unique<E>

typealias NullableColumn<E> = TableSpecification.ColumnsSpecification.ColumnModifier.Nullable<E>