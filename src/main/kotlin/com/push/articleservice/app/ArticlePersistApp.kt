package com.push.articleservice.app

import com.push.articleservice.eventpublisher.ArticleEventPublisher
import com.push.articleservice.model.ReadArticleCommand
import com.push.articleservice.model.ReadArticleQuery
import com.push.articleservice.persistence.ArticlePersistence
import org.springframework.stereotype.Component
import java.util.stream.Stream

@Component
class ArticlePersistApp(
    private val articlePersistence: ArticlePersistence,
    private val articleEventPublisher: ArticleEventPublisher) {

    fun persistArticleReadEvent(readArticleCommand: ReadArticleCommand): Stream<ReadArticleQuery> {
        return Stream.of(readArticleCommand)
            .map { articlePersistence.persistCommand(it) }
            .map { articleEventPublisher.publishArticleEvent(it) }
    }
}