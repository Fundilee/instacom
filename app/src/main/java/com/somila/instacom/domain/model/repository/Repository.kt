package com.somila.instacom.domain.model.repository

import com.somila.instacom.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getPosts() : Result<List<Post>>

    suspend fun createPost(postRequest: Post): Result<Post>

    suspend fun updatePost(id: Int, postRequest: Post) : Result<Post>

    suspend fun deletePost(id: Int) : Result<Unit>
}