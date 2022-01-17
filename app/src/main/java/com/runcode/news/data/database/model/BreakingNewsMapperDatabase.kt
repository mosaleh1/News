package com.runcode.news.data.database.model

import com.runcode.news.data.model.BreakingNews
import com.runcode.news.util.EntityMapper
import java.util.*
import javax.inject.Inject

class BreakingNewsMapperDatabase @Inject constructor() :
    EntityMapper<BreakingNewsDatabase, BreakingNews> {
    
    // Entity  ->Domain
    override fun fromEntityToDomain(entity: BreakingNewsDatabase): BreakingNews {
        return BreakingNews(
            id = entity.id ?: Random(10000).nextInt().toString(),
            name = entity.name ?: "Not Found",
            author = entity.author ?: "Not Found",
            title = entity.title ?: "Not Found",
            description = entity.description ?: "Not Found",
            content = entity.content ?: "Not Found",
            publishedAt = entity.publishedAt ?: "Not Found",
            urlToImage = entity.urlToImage?: "Not Found",
            articleUrl = entity.articleUrl,
            isFavorite = entity.isFavorite,
            topic = entity.topic
        )
    }
    // Domain -> Entity
    override fun fromDomainToEntity(domainModel: BreakingNews): BreakingNewsDatabase {
        return BreakingNewsDatabase(
            id = if (domainModel.id.isNotEmpty() || domainModel.id !="null") domainModel.id else
                Random(10000).nextInt().toString(),
            name = domainModel.name,
            author = domainModel.author,
            title = domainModel.title,
            description = domainModel.description,
            content = domainModel.content,
            publishedAt = domainModel.publishedAt,
            urlToImage = domainModel.urlToImage,
            articleUrl = domainModel.articleUrl,
            isFavorite = domainModel.isFavorite,
            topic = domainModel.topic
        )
    }

    // Entity List -> Domain List
    fun fromListOfEntityToBreakingNewsList(entities: List<BreakingNewsDatabase>): List<BreakingNews> {
        return entities.map { fromEntityToDomain(it) }
    }

    // Domain List -> Entity List
    fun fromBreakingNewsListToListOfEntity(entities: List<BreakingNews>): List<BreakingNewsDatabase> {
        return entities.map { fromDomainToEntity(it) }
    }

//    // Domain topic ->Entity topic
//    private fun fromBreakingNewsByTopicToEntity(domainModel: BreakingNews, topic:String): BreakingNewsDatabase{
//        return BreakingNewsDatabase(
//            id = if (domainModel.id.isNotEmpty() || domainModel.id !="null") domainModel.id else
//                Random(10000).nextInt().toString(),
//            name = domainModel.name,
//            author = domainModel.author,
//            title = domainModel.title,
//            description = domainModel.description,
//            content = domainModel.content,
//            publishedAt = domainModel.publishedAt,
//            urlToImage = domainModel.urlToImage,
//            articleUrl = domainModel.articleUrl,
//            topic = topic
//        )
//    }
//
//    // Domain List topic ->Entity List topic
//    fun fromEntitiesToBreakingNewsByTopic(entities: List<BreakingNews>,topic: String): List<BreakingNewsDatabase> {
//        return entities.map { fromBreakingNewsByTopicToEntity(it,topic) }
//    }
//
//    // Entity topic ->Domain topic
//    private fun fromEntityToDomainByTopic(entity: BreakingNewsDatabase): BreakingNews {
//        return BreakingNews(
//            id = entity.id ?: Random(10000).nextInt().toString(),
//            name = entity.name ?: "Not Found",
//            author = entity.author ?: "Not Found",
//            title = entity.title ?: "Not Found",
//            description = entity.description ?: "Not Found",
//            content = entity.content ?: "Not Found",
//            publishedAt = entity.publishedAt ?: "Not Found",
//            urlToImage = entity.urlToImage?: "Not Found",
//            articleUrl = entity.articleUrl,
//            topic = entity.topic
//        )
//    }
//
//    // Entity List topic ->Domain List topic
//    fun fromEntitiesToBreakingNewsByTopic(entities: List<BreakingNewsDatabase>): List<BreakingNews> {
//        return entities.map { fromEntityToDomainByTopic(it) }
//    }
//
//
//    //fav
//
//    fun fromDomainFavToEntityFav (domainModel: BreakingNews):BreakingNewsDatabase{
//        return BreakingNewsDatabase(
//            id = domainModel.id,
//             name = domainModel.name,
//            author = domainModel.author,
//            title = domainModel.title,
//            description = domainModel.description,
//            content = domainModel.content,
//            publishedAt = domainModel.publishedAt,
//            urlToImage = domainModel.urlToImage,
//            articleUrl = domainModel.urlToImage,
//            isFavorite = domainModel.isFavorite,
//            topic = domainModel.topic
//        )
//    }
//

}