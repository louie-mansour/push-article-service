package com.push.articleservice.model

import java.time.Instant

data class ReadArticleQuery(
    val url: String,
    val content: String,
    val titleForDisplay: String,
    val uploadedInstant: Instant,
    val articleTrends: List<ArticleTrendData>
)

data class ArticleTrendData(
    val titleForAnalysis: String,
    val articleSource: String,
    val comments: List<String>,
    val publishedInstant: Instant?,
    val author: String? = null,
    val upVotes: Int? = null,
    val downVotes: Int? = null,
    val numberOfReads: Int? = null,
    val readInstant: Instant
)