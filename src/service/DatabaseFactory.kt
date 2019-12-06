package com.fridayhack.service

import com.fridayhack.models.*
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

object DatabaseFactory {

    fun init() {
        Database.connect(hikariPG())
        migrate()
        fixtures()
    }

    private fun migrate() {
        transaction {
            SchemaUtils.createMissingTablesAndColumns(Pages)
        }
    }

    private fun fixtures() {
        transaction {
            Page.find { Pages.slug eq "db-page" }.firstOrNull()
                ?: Page.new {
                    slug = "db-page"
                    body = "<html><h1>Hello world!</h1></h5>from DB</h5></html>"
                }
        }
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