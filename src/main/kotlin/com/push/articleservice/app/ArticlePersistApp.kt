package com.push.articleservice.app

import com.push.articleservice.eventlistener.ArticleEventListener
import com.push.articleservice.eventpublisher.ArticleEventPublisher
import com.push.articleservice.model.ReadArticleQuery
import com.push.articleservice.persistence.ArticlePersistence
import org.springframework.stereotype.Component
import java.util.stream.Stream

@Component
class ArticlePersistApp(
    private val articlePersistence: ArticlePersistence,
    private val articleEventPublisher: ArticleEventPublisher) {

    fun persistArticleReadEvent(): Stream<String> {
        return Stream.of("test")
//            .map { articlePersistence.persistCommand(it) }
//            .map { articleEventPublisher.publishArticleEvent(it) }
    }
}