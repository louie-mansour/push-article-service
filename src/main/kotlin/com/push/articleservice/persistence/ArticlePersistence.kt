package com.push.articleservice.persistence

import com.push.articleservice.model.ArticleTrendData
import com.push.articleservice.model.ReadArticleCommand
import com.push.articleservice.model.ReadArticleQuery
import com.push.articleservice.persistence.record.ArticleDataRecord
import com.push.articleservice.persistence.record.ArticleRecord
import com.push.articleservice.persistence.repo.ArticleRepo
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp


@Component
class ArticlePersistence(private val articleRepo: ArticleRepo) {

    @Transactional
    fun persistCommand(readArticleCommand: ReadArticleCommand): ReadArticleQuery {
        val (articleCommandRecord, articleDataCommandRecord) = transform(readArticleCommand)
        val url = articleCommandRecord.url
        articleRepo.upsertArticle(url, articleCommandRecord.content)
        articleRepo.insertArticleData(
            url = url,
            articleSource = articleDataCommandRecord.articleSource,
            titleForDisplay = articleDataCommandRecord.titleForDisplay,
            titleForAnalysis = articleDataCommandRecord.titleForAnalysis,
            uploadedInstant = Timestamp.from(articleDataCommandRecord.uploadedInstant),
            readInstant = Timestamp.from(articleDataCommandRecord.readInstant),
            comments = articleDataCommandRecord.comments.toTypedArray(),
            author = articleDataCommandRecord.author,
            upVotes = articleDataCommandRecord.upVotes,
            downVotes = articleDataCommandRecord.downVotes,
            numberOfReads = articleDataCommandRecord.numberOfReads,
            publishedInstant = Timestamp.from(articleDataCommandRecord.publishedInstant),
        )
        val articleQueryRecord = articleRepo.selectArticle(url)
        val articleDataQueryRecords = articleRepo.selectArticleDataEvents(url)
        println(url)
        return transform(articleQueryRecord, articleDataQueryRecords)
    }

    private fun transform(readArticleCommand: ReadArticleCommand): Pair<ArticleRecord, ArticleDataRecord> {
        return Pair(
            ArticleRecord(
                url = readArticleCommand.url,
                content = readArticleCommand.content
            ),
            ArticleDataRecord(
                articleSource = readArticleCommand.articleSource,
                url = readArticleCommand.url,
                titleForDisplay = readArticleCommand.titleForDisplay,
                titleForAnalysis = readArticleCommand.titleForAnalysis,
                uploadedInstant = readArticleCommand.uploadedInstant,
                comments = readArticleCommand.comments,
                publishedInstant = readArticleCommand.publishedInstant,
                author = readArticleCommand.author,
                upVotes = readArticleCommand.upVotes,
                downVotes = readArticleCommand.downVotes,
                numberOfReads = readArticleCommand.numberOfReads,
                readInstant = readArticleCommand.readInstant
            )
        )
    }

    private fun transform(articleRecord: ArticleRecord, articleDataRecords: List<ArticleDataRecord>): ReadArticleQuery {
        val newestArticleDataRecord = articleDataRecords.first()
        return ReadArticleQuery(
            url = articleRecord.url,
            content = articleRecord.content,
            titleForDisplay = newestArticleDataRecord.titleForDisplay,
            uploadedInstant = newestArticleDataRecord.uploadedInstant,
            articleTrends = articleDataRecords.map {
                ArticleTrendData(
                    titleForAnalysis = it.titleForAnalysis,
                    articleSource = it.articleSource,
                    comments = it.comments,
                    publishedInstant = it.publishedInstant,
                    author = it.author,
                    upVotes = it.upVotes,
                    downVotes = it.downVotes,
                    numberOfReads = it.numberOfReads,
                    readInstant = it.readInstant
                )
            }
        )
    }
}