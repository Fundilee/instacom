package com.somila.domain.repository

import com.somila.domain.model.Post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface PostRepository {

    suspend fun getPost(id: Int): Result<Post>
    fun observePosts(): StateFlow<List<Post>>

    suspend fun getPosts(): Result<Unit>
    suspend fun createPost(postRequest: Post): Result<Post>
    suspend fun updatePost(id: Int, postRequest: Post) : Result<Post>
    suspend fun deletePost(id: Int) : Result<Unit>
}