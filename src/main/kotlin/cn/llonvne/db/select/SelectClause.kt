package cn.llonvne.db.select

import cn.llonvne.db.clause.Clause

class SelectClause(
    val fromClause: FromClause,
    val fieldsClause: FieldsClause
) : Clause {

    override fun toString(): String {
        return "SELECT $fieldsClause FROM $fromClause"
    }
}