package cn.llonvne.db.clause

data class TableClause<T : Any>(
    val schema: String,
    val tableName: String,
    val clauses: List<Clause>,
) : Clause {
    override fun toString(): String =
        """create table ${Clause.schemaTable(schema, tableName)}
(
${clauses.joinToString(",\n")}
);"""
}