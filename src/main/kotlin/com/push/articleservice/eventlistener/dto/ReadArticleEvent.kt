package com.push.articleservice.eventlistener.dto

data class ReadArticleEvent(
    val articleSource: String,
    val url: String,
    val titleForDisplay: String,
    val titleForAnalysis: String,
    val content: String,
    val uploadedInstant: Int,
    val comments: List<String>,
    val readInstant: Int,
    val publishedInstant: Int?,
    val author: String? = null,
    val upVotes: Int? = null,
    val downVotes: Int? = null,
    val numberOfReads: Int? = null
)
