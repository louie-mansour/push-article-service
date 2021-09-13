package com.push.articleservice.eventpublisher

import com.push.articleservice.model.ReadArticleQuery
import org.springframework.stereotype.Component

@Component
class ArticleEventPublisher {
    fun publishArticleEvent(readArticleQuery: ReadArticleQuery): ReadArticleQuery {
        return readArticleQuery
    }
}