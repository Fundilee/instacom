package com.somila.data.di.mapper

import com.somila.domain.model.Post
import com.somila.network.di.dto.PostDto

object PostMapper {
    fun mapToDomain(dto: PostDto): Post {
        return Post(
            id = dto.id,
            title = dto.title,
            body = dto.body,
            updatedAt = dto.updatedAt,
            createdAt = dto.createdAt
        )
    }

    fun mapToDomainList(dtoList: List<PostDto>): List<Post> {
        return dtoList.map { mapToDomain(it) }
    }
}