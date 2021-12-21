package com.runcode.news.util

interface EntityMapper <Entity,DomainModel> {

    fun fromEntityToDomain (entity: Entity): DomainModel
    fun fromDomainToEntity (domainModel: DomainModel): Entity
}