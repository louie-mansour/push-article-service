package com.push.articleservice.persistence.record

import java.time.Instant

class ArticleDataRecord(
    val articleSource: String,
    val url: String,
    val titleForDisplay: String,
    val titleForAnalysis: String,
    val uploadedInstant: Instant,
    val comments: List<String>,
    val publishedInstant: Instant? = null,
    val author: String? = null,
    val upVotes: Int? = null,
    val downVotes: Int? = null,
    val numberOfReads: Int? = null,
    val readInstant: Instant = Instant.now()
)