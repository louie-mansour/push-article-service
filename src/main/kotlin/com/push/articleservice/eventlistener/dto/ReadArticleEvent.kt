package com.push.articleservice.eventlistener.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ReadArticleEvent(
    @JsonProperty("articleSource") val articleSource: String,
    @JsonProperty("url") val url: String,
    @JsonProperty("titleForDisplay") val titleForDisplay: String,
    @JsonProperty("titleForAnalysis") val titleForAnalysis: String,
    @JsonProperty("content") val content: String,
    @JsonProperty("uploadedInstant") val uploadedInstant: Int,
    @JsonProperty("comments") val comments: List<String>,
    @JsonProperty("readInstant") val readInstant: Int,
    @JsonProperty("publishedInstant") val publishedInstant: Int?,
    @JsonProperty("author") val author: String? = null,
    @JsonProperty("upVotes") val upVotes: Int? = null,
    @JsonProperty("downVotes") val downVotes: Int? = null,
    @JsonProperty("numberOfReads") val numberOfReads: Int? = null
)
