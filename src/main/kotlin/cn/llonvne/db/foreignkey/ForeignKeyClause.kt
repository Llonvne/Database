package cn.llonvne.db.foreignkey

import cn.llonvne.db.clause.Clause

data class ForeignKeyClause<T : Any>(
    val context: ForeignKeyContext,
    val foreignKey: ForeignKeySpecification.ForeignKey<T, *, *>
) : Clause {
    override fun toString(): String {

        return "FOREIGN KEY (${context.resolveLocalColumnName(foreignKey.local)}) REFERENCES ${
            foreignKey.foreignTableDefinition.context().resolveTableName()
        }(${
            context.resolveForeignColumnName(
                foreignKey.foreignTableDefinition,
                foreignKey.foreign
            )
        }) " + resolveCascade()
    }

    fun resolveCascade(): String {
        if (foreignKey.cascade == null) {
            return ""
        }
        return ForeignKeyCascadeClause(foreignKey.cascade).toString()
    }
}

data class ForeignKeyCascadeClause(
    private val cascade: ForeignKeySpecification.CascadeSpecification
) {
    override fun toString(): String {
        return """ON ${cascade.onWhat()} ${cascade.operation}"""
    }
}
