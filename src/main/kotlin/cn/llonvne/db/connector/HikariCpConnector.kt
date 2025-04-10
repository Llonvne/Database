package cn.llonvne.db.connector

import cn.llonvne.db.connection.Connection
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource

class HikariCpConnector(
    override val url: String,
    override val username: String,
    override val password: String,
    cfgOpt: HikariConfig.() -> Unit = {},
) : Connector,
    ConnectByUrlUsernamePassword {
    constructor(from: ConnectByUrlUsernamePassword, opt: HikariConfig.() -> Unit = {}) : this(
        from.url,
        from.username,
        from.password,
        opt,
    )

    private val cfg = HikariConfig()

    init {
        cfg.jdbcUrl = url
        cfg.username = username
        cfg.password = password
        cfg.apply { cfgOpt() }
    }

    private val ds = HikariDataSource(cfg)

    override fun connect(): Connection = Connection.wrap(ds.connection)

    override fun type(): ConnectorType = ConnectorType.Pooled
}
