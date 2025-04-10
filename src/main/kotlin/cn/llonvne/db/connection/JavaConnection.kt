package cn.llonvne.db.connection

import cn.llonvne.db.connection.ops.ConnectionOps
import cn.llonvne.db.connection.ops.JavaConnectionOps
import java.sql.Connection as JConn

class JavaConnection(
    private val conn: JConn,
) : Connection,
    ConnectionOps by JavaConnectionOps(conn) {
    override fun close() {
        conn.close()
    }
}
