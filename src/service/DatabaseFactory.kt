package com.fridayhack.service

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.*
import java.util.*

object DatabaseFactory {

    fun init() {
        Database.connect(hikariPG())
    }

    private fun hikariPG(): HikariDataSource {
        val props = Properties()
        props.setProperty("dataSourceClassName", "org.postgresql.ds.PGSimpleDataSource");
        props.setProperty("dataSource.user", "postgres")
        props.setProperty("dataSource.password", "postgres")
        props.setProperty("dataSource.databaseName", "fridaydummy_dev")
        props.setProperty("dataSource.portNumber", "5432")
        props.setProperty("dataSource.serverName", "localhost")

        val config = HikariConfig(props)

        config.validate()

        return HikariDataSource(config)
    }

}