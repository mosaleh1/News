package com.runcode.news.data.database.model

import com.runcode.news.data.model.BreakingNews
import com.runcode.news.util.EntityMapper
import javax.inject.Inject

class BreakingNewsMapperDatabase @Inject constructor() :
    EntityMapper<BreakingNewsDatabase, BreakingNews> {
    override fun fromEntityToDomain(entity: BreakingNewsDatabase): BreakingNews {
        return BreakingNews(
            id = entity.id ?:"0",
            name = entity.name ?: "Not Found",
            author = entity.author ?: "Not Found",
            title = entity.title ?: "Not Found",
            description = entity.description ?: "Not Found",
            content = entity.content ?: "Not Found",
            publishedAt = entity.publishedAt ?: "Not Found",
            urlToImage = entity.urlToImage?: "Not Found",
            articleUrl = entity.articleUrl
        )
    }

    override fun fromDomainToEntity(domainModel: BreakingNews): BreakingNewsDatabase {
        return BreakingNewsDatabase(
            id = domainModel.id,
            name = domainModel.name,
            author = domainModel.author,
            title = domainModel.title,
            description = domainModel.description,
            content = domainModel.content,
            publishedAt = domainModel.publishedAt,
            urlToImage = domainModel.urlToImage,
            articleUrl = domainModel.articleUrl
        )
    }

    fun fromListOfEntityToBreakingNewsList(entities: List<BreakingNewsDatabase>): List<BreakingNews> {
        return entities.map { fromEntityToDomain(it) }
    }

    fun fromBreakingNewsListToListOfEntity(entities: List<BreakingNews>): List<BreakingNewsDatabase> {
        return entities.map { fromDomainToEntity(it) }
    }
}