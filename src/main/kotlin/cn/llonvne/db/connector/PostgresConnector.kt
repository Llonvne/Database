package cn.llonvne.db.connector

import cn.llonvne.db.connection.Connection
import java.sql.DriverManager

class PostgresConnector(
    override val url: String,
    override val username: String,
    override val password: String,
) : Connector,
    ConnectByUrlUsernamePassword {
    override fun connect(): Connection {
        val conn = DriverManager.getConnection(url, username, password)
        return Connection.wrap(conn)
    }

    override fun type(): ConnectorType = ConnectorType.Raw
}
