package com.runcode.news.data.database.model

import com.runcode.news.data.model.BreakingNews
import com.runcode.news.data.model.Headlines
import com.runcode.news.util.EntityMapper
import javax.inject.Inject

class HeadlinesMapperDatabase @Inject constructor() : EntityMapper<HeadlinesDatabase, Headlines> {

    override fun fromEntityToDomain(entity: HeadlinesDatabase): Headlines {
        return Headlines(
            id = entity.id,
            name = entity.name,
            author = entity.author,
            title = entity.title,
            description = entity.description,
            content = entity.content,
            publishedAt = entity.publishedAt,
            urlToImage = entity.urlToImage,
            articleUrl = entity.articleUrl
        )
    }


    override fun fromDomainToEntity(domainModel: Headlines): HeadlinesDatabase {
        return HeadlinesDatabase(
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

    fun fromListOfEntityToHeadlinesList(entities: List<HeadlinesDatabase>): List<Headlines> {
        return entities.map { fromEntityToDomain(it) }
    }


    fun fromHeadlinesListToListOfEntity(entities: List<Headlines>): List<HeadlinesDatabase> {
        return entities.map { fromDomainToEntity(it) }
    }


}