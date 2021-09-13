package com.push.articleservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement
class ArticleServiceApplication

fun main(args: Array<String>) {
	runApplication<ArticleServiceApplication>(*args)
}
