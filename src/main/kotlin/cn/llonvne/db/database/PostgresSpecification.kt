package cn.llonvne.db.database

class PostgresSpecification(
) : DatabaseSpecification{
    override fun schema(): String {
        return "public"
    }
}
