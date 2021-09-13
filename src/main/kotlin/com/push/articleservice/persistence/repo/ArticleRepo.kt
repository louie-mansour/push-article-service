package com.push.articleservice.persistence.repo

import com.push.articleservice.persistence.record.ArticleDataRecord
import com.push.articleservice.persistence.record.ArticleRecord
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.sql.Timestamp

@Repository
interface ArticleRepo : CrudRepository<ArticleRecord, Long> {

    @Modifying
    @Query("""
        INSERT INTO article (url, content)
        VALUES (:url, :content)
        ON CONFLICT (url)
        DO UPDATE SET content = EXCLUDED.content
    """)
    fun upsertArticle(url: String, content: String): Int

    @Modifying
    @Query("""
        INSERT INTO article_data(url, article_source, title_for_display, title_for_analysis, uploaded_instant,
            read_instant, comments, author, up_votes, down_votes, number_of_reads)
        VALUES(:url, :articleSource, :titleForDisplay, :titleForAnalysis, :uploadedInstant, :readInstant, :comments,
            :author, :upVotes, :downVotes, :numberOfReads);
    """)
    fun insertArticleData(url: String, articleSource: String, titleForDisplay: String, titleForAnalysis: String,
        uploadedInstant: Timestamp, readInstant: Timestamp, comments: Array<String>, author: String?, upVotes: Int?,
        downVotes: Int?, numberOfReads: Int?, publishedInstant: Timestamp
    ): Int

    @Query("""SELECT * FROM article WHERE article.url = :url""")
    fun selectArticle(url: String): ArticleRecord

    @Query("""SELECT * FROM article_data where article_data.url = :url""")
    fun selectArticleDataEvents(url: String): List<ArticleDataRecord>
}