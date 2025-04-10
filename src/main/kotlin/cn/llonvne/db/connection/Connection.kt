package cn.llonvne.db.connection

import cn.llonvne.db.connection.ops.ConnectionOps

interface Connection :
    AutoCloseable,
    ConnectionOps {
    companion object {
        fun wrap(conn: java.sql.Connection): Connection = JavaConnection(conn)
    }
}
