package cn.llonvne.db.select

import cn.llonvne.db.clause.Clause

class FieldsClause(
    val selectContext: SelectContext,
    val fields: SelectSpecification.Fields
) : Clause {
    override fun toString(): String {
        return fields.fields.joinToString(",") {
            selectContext.getTableAlias(selectContext.resolveTableFromProperty(it)) + "." + selectContext.resolveColumnName(it)
        }
    }
}