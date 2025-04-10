package cn.llonvne.db

import cn.llonvne.db.connector.Connector

class Database(
    private val connector: Connector,
) {
    init {
        connector.use {
        }
    }
}
