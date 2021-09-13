package com.push.articleservice.eventlistener

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.pubsub.v1.PubsubMessage
import com.push.articleservice.eventlistener.dto.ReadArticleEvent
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets

@Component
class ArticleEventListener(private val pubSubTemplate: PubSubTemplate): ApplicationListener<ApplicationStartedEvent> {

    override fun onApplicationEvent(_event: ApplicationStartedEvent) {
        println("Beginning subscription")
        pubSubTemplate.subscribe("projects/push-to-date/subscriptions/test") { message ->
            println("start of message")
            println(transform(message.pubsubMessage))
            println("transformed")
            message.ack()
            println("acked")
        }
    }

    private fun transform(pubSubMessage: PubsubMessage): ReadArticleEvent {
        return objectReader.readValue(
            String(pubSubMessage.data.toByteArray(), StandardCharsets.UTF_8), ReadArticleEvent::class.java
        )
    }

    companion object {
        private val objectReader = ObjectMapper().reader()
    }
}