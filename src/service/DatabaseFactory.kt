package com.fridayhack.service

import com.fridayhack.models.*
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

fun databaseConfigFromEnv() = DatabaseConfig(
    port = System.getenv("DB_PORT") ?: "5432",
    name = System.getenv("DB_NAME") ?: "fridaydummy_dev",
    host = System.getenv("DB_HOST") ?: "localhost",
    user = System.getenv("DB_USER") ?: "postgres",
    pass = System.getenv("DB_PASS") ?: "postgres"
)

data class DatabaseConfig(
    val port: String,
    val name: String,
    val host: String,
    val user: String,
    val pass: String
)

object DatabaseFactory {

    fun init(config: DatabaseConfig) {
        Database.connect(hikariPG(config))
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

    private fun hikariPG(config: DatabaseConfig): HikariDataSource {
        val props = Properties()

        props.setProperty("dataSourceClassName", "org.postgresql.ds.PGSimpleDataSource");
        props.setProperty("dataSource.user", config.user)
        props.setProperty("dataSource.password", config.pass)
        props.setProperty("dataSource.databaseName", config.name)
        props.setProperty("dataSource.portNumber", config.port)
        props.setProperty("dataSource.serverName", config.host)

        val config = HikariConfig(props)

        config.validate()

        return HikariDataSource(config)
    }

}