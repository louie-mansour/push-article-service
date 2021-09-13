package com.push.articleservice.web

import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Component
@RestController
class ArticleController {

    @GetMapping("/test")
    fun test(): String {
        return "test"
    }
}