package cn.llonvne.db.connector

import cn.llonvne.db.connection.Connection

interface Connector {
    fun connect(): Connection

    fun type(): ConnectorType

    fun <R> use(action: Connection.() -> R): R = connect().use { action(it) }
}
