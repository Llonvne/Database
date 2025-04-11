package cn.llonvne.db.clause

interface Clause {
    companion object {
        fun schemaTable(schema: String, table: String) = "$schema.\"$table\""
    }
}