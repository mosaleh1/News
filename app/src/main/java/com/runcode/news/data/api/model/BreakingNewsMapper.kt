package com.runcode.news.data.api.model

import com.runcode.news.data.model.BreakingNews
import com.runcode.news.data.model.BreakingNewsModel
import com.runcode.news.data.model.Headlines
import com.runcode.news.util.EntityMapper

class BreakingNewsMapper : EntityMapper<BreakingNewsNetworkEntity, BreakingNewsModel> {
    override fun fromEntityToDomain(entity: BreakingNewsNetworkEntity): BreakingNewsModel {
        return BreakingNewsModel(
            articles = mapFromListOfArticlesToBreakingNews(entity.articles),
            status = entity.status,
            totalResults = entity.totalResults
        )
    }

    override fun fromDomainToEntity(domainModel: BreakingNewsModel): BreakingNewsNetworkEntity {
        return BreakingNewsNetworkEntity(
            articles = mapFromListOfBreakingNewsToArticle(domainModel.articles),
            status = domainModel.status,
            totalResults = domainModel.totalResults
        )
    }


    private fun fromArticlesToBreakingNews(article: Article): BreakingNews {
        return BreakingNews(
            id = article.source?.id?:"Not found",
            name = article.source?.name ?:"Not found",
            author = article.author ?:"Not found",
            title = article.title?:"Not found",
            description = article.description?:"Not found",
            content = article.content?:"Not found",
            publishedAt = article.publishedAt?:"Not found",
            urlToImage = article.urlToImage?:"Not found",
            articleUrl = article.url
        )
    }

    fun mapFromListOfArticlesToBreakingNews(articles: List<Article>): List<BreakingNews> {
        return articles.map { fromArticlesToBreakingNews(it) }
    }

    fun mapFromDomainModelToEntity(breakingNews: BreakingNews): Article {
        return Article(
            author = breakingNews.author,
            content = breakingNews.content,
            description = breakingNews.description,
            publishedAt = breakingNews.publishedAt,
            Source(id = breakingNews.id, name = breakingNews.name),
            title = breakingNews.title,
            urlToImage = breakingNews.urlToImage,
            url = breakingNews.articleUrl
        )
    }

    fun mapFromListOfBreakingNewsToArticle(breakingNews: List<BreakingNews>): List<Article> {
        return breakingNews.map { mapFromDomainModelToEntity(it) }
    }

}