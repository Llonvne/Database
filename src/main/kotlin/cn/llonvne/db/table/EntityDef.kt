package cn.llonvne.db.table

interface EntityDef<T : EntityDef<T>> {
    fun tableDefinition(): TableDefinition<T>
}