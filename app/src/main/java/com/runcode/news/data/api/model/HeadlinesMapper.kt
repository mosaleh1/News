package com.runcode.news.data.api.model

import com.runcode.news.data.model.BreakingNewsModel
import com.runcode.news.util.EntityMapper
import com.runcode.news.data.model.Headlines
import com.runcode.news.data.model.HeadlinesModel
import javax.inject.Inject

class HeadlinesMapper @Inject constructor()  : EntityMapper<HeadlineNetworkEntity, HeadlinesModel>
{

    override fun fromEntityToDomain(entity: HeadlineNetworkEntity): HeadlinesModel {
        return HeadlinesModel(
            articles = mapFromListOfArticlesToHeadlines(entity.articles),
            status = entity.status,
            totalResults = entity.totalResults
        )
    }


    override fun fromDomainToEntity(domainModel: HeadlinesModel): HeadlineNetworkEntity {
        return HeadlineNetworkEntity(
            articles = mapFromListOfHeadlinesToArticle(domainModel.articles),
            status = domainModel.status,
            totalResults = domainModel.totalResults
        )
    }


    private fun fromArticlesToHeadlines(article: Article): Headlines {
        return Headlines(
            id = article.source.id?:"Not found",
            name = article.source.name?:"Not found",
            author = article.author?:"Not found",
            title = article.title?:"Not found",
            description = article.description?:"Not found",
            content = article.content?:"Not found",
            publishedAt = article.publishedAt?:"Not found",
            urlToImage = article.urlToImage?:"Not found",
            articleUrl = article.url
        )
    }

    private fun mapFromListOfArticlesToHeadlines(articles: List<Article>): List<Headlines> {
        return articles.map { fromArticlesToHeadlines(it) }
    }

    private fun mapFromHeadlinesModelToArticles(headlines: Headlines): Article {
        return Article(
            author = headlines.author,
            content = headlines.content,
            description = headlines.description,
            publishedAt = headlines.publishedAt,
            Source(id = headlines.id, name = headlines.name),
            title = headlines.title,
            urlToImage = headlines.urlToImage,
            url = headlines.articleUrl
        )
    }

    private fun mapFromListOfHeadlinesToArticle(headlines: List<Headlines>): List<Article> {
        return headlines.map { mapFromHeadlinesModelToArticles(it) }
    }
}