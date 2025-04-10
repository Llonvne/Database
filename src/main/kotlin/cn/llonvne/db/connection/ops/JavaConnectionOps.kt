package cn.llonvne.db.connection.ops

import java.sql.Connection

class JavaConnectionOps(
    private val connection: Connection,
) : ConnectionOps {
    override fun close() {
        connection.close()
    }
}
