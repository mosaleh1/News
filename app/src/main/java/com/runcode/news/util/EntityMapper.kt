package com.runcode.news.util

import com.runcode.news.data.database.model.BreakingNewsDatabase
import com.runcode.news.data.model.BreakingNews

interface EntityMapper <Entity,DomainModel> {

    fun fromEntityToDomain (entity: Entity): DomainModel
    fun fromDomainToEntity (domainModel: DomainModel): Entity

}