package cn.llonvne.db

import cn.llonvne.db.database.DatabaseSpecification.Companion.define
import cn.llonvne.db.database.PostgresSpecification
import cn.llonvne.db.foreignkey.ForeignKeySpecification
import cn.llonvne.db.foreignkey.ForeignKeySpecification.CascadeOperationSpecification.Cascade
import cn.llonvne.db.foreignkey.ForeignKeySpecification.CascadeSpecification.OnUpdate
import cn.llonvne.db.registry.TableDefinitionRegistry
import cn.llonvne.db.select.fields
import cn.llonvne.db.select.from
import cn.llonvne.db.select.select
import cn.llonvne.db.select.where
import cn.llonvne.db.table.*
import cn.llonvne.db.table.ColumnName
import cn.llonvne.db.table.PrimaryKeyStrategy
import cn.llonvne.db.table.TableSpecification.ColumnsSpecification.ColumnModifier.*
import cn.llonvne.db.table.TableSpecification.ColumnsSpecification.ColumnModifier.PrimaryKey
import cn.llonvne.db.table.TableSpecification.EmptyConstructor
import cn.llonvne.db.table.TableSpecification.TableName


data class User(
    val username: String,
    val id: Long,
)

data class Article(
    val content: String,
    val ownerUserId: Long
)

fun main() {
//    Database(HikariCpConnector("jdbc:postgresql://localhost:5432/", "admin", "admin"))

    val postgresSpecification = PostgresSpecification()

    val articleDefine = postgresSpecification.define<Article>(
        TableName("tb_article"),
        Article::content.varchar(20000, DefaultValue(""), Nullable(false)),
        Article::ownerUserId.long(Nullable(false), UniqueConstraint())
    )

    val userDefine = postgresSpecification.define<User>(
        TableName("tb-user"),
        EmptyConstructor {
            User("", 0)
        },
        User::username.varchar(
            255,
            UniqueConstraint("12"),
            PrimaryKey(PrimaryKeyStrategy.Single),
            NotNull(),
            DefaultValue("1")
        ),
        User::id.long(
            ColumnName("user_id"),
            DefaultValue(1)
        ),
        User::id.foreignKey(
            articleDefine,
            Article::ownerUserId,
            OnUpdate(Cascade)
        )
    )


    println(
        postgresSpecification.select(
            from(userDefine, articleDefine),
            fields(
                Article::content
            ),
            where {
                User::username.eq { "Llonvne" } and User::id.eq { 1 }
            },
        )
    )

    println(articleDefine.ddl())

    print(userDefine.ddl())
}
