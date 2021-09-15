package com.push.articleservice.eventlistener

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.google.pubsub.v1.PubsubMessage
import com.push.articleservice.app.ArticlePersistApp
import com.push.articleservice.eventlistener.dto.ReadArticleEvent
import com.push.articleservice.model.ReadArticleCommand
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.util.stream.Collectors.toList

@Component
class ArticleEventListener(
    private val pubSubTemplate: PubSubTemplate,
    private val articlePersistApp: ArticlePersistApp): ApplicationListener<ApplicationStartedEvent> {

    override fun onApplicationEvent(_event: ApplicationStartedEvent) {
        println("Beginning subscription")
        pubSubTemplate.subscribe("projects/push-to-date/subscriptions/test") { message ->
            try {
                println("start of message")
                val articleTrend = articlePersistApp.persistArticleReadEvent(transform(message.pubsubMessage))
                    .collect(toList()).first()
                print("processed message: $articleTrend")
                message.ack()
            } catch (ex: Exception) {
                message.nack()
                print("nacking message with exception: $ex")
            }
            message.ack()
            println("acked")
        }
    }

    private fun transform(pubSubMessage: PubsubMessage): ReadArticleCommand {
        println("String format: ${String(pubSubMessage.data.toByteArray(), StandardCharsets.UTF_8)}")
        val readArticleEvent = objectReader.readValue(
            String(pubSubMessage.data.toByteArray(), StandardCharsets.UTF_8), ReadArticleEvent::class.java)
        return ReadArticleCommand(
            articleSource = readArticleEvent.articleSource,
            url = readArticleEvent.url,
            content = readArticleEvent.content,
            titleForDisplay = readArticleEvent.titleForDisplay,
            titleForAnalysis = readArticleEvent.titleForAnalysis,
            uploadedInstant = Instant.ofEpochSecond(readArticleEvent.uploadedInstant.toLong()),
            comments = readArticleEvent.comments,
            publishedInstant = if(readArticleEvent.publishedInstant != null) { Instant.ofEpochSecond(readArticleEvent.publishedInstant.toLong()) } else { null },
            author = readArticleEvent.author,
            upVotes = readArticleEvent.upVotes,
            downVotes = readArticleEvent.downVotes,
            numberOfReads = readArticleEvent.numberOfReads,
            readInstant = Instant.ofEpochSecond(readArticleEvent.readInstant.toLong())
        )
    }

    companion object {
        private val objectReader = ObjectMapper().registerKotlinModule().reader()
    }
}