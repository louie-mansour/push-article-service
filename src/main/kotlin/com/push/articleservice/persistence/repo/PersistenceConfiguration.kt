package com.push.articleservice.persistence.repo

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import javax.sql.DataSource


@Configuration
@Profile("prod")
class PersistenceConfiguration(
    @Value("\${app.database.name}") private val name: String,
    @Value("\${app.database.user}") private val user: String,
    @Value("\${app.database.password}") private val password: String
    ) {

    @Bean
    fun dataSource(): DataSource {
        val config = HikariConfig()
        config.jdbcUrl = "jdbc:postgresql:///$name"
        config.username = user
        config.password = password
        config.addDataSourceProperty("socketFactory", "com.google.cloud.sql.postgres.SocketFactory")
        config.addDataSourceProperty("cloudSqlInstance", "push-to-date:northamerica-northeast1:push-article-service-database")
        config.addDataSourceProperty("ipTypes", "PUBLIC,PRIVATE")
        return HikariDataSource(config)
    }
}