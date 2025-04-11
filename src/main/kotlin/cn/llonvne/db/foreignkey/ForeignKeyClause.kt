package cn.llonvne.db.foreignkey

import cn.llonvne.db.clause.Clause

data class ForeignKeyClause<T : Any>(val foreignKey: ForeignKeySpecification.ForeignKey<T, *, *>) : Clause {
    override fun toString(): String {
        TODO()
    }
}

