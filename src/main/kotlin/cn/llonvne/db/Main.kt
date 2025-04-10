package cn.llonvne.db

import cn.llonvne.db.connector.HikariCpConnector
import cn.llonvne.db.table.TableDefinition
import cn.llonvne.db.table.TableSpecification.EmptyConstructor
import cn.llonvne.db.table.varchar
import cn.llonvne.db.table.*

data class User(
    val username: String,
    val id: Long
)

fun main() {
    Database(HikariCpConnector("jdbc:postgresql://localhost:5432/", "admin", "admin"))

    val userDefine =
        TableDefinition.define<User>(

            TableName("tb-user"),

            EmptyConstructor {
                User("", 0)
            },

            User::username.varchar(
                255,
                UniqueConstraint(),
                PrimaryKey(PrimaryKeyStrategy.Single)
            ),
            User::id.long(

            )
        )
}
