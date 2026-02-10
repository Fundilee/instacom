package com.somila.domain.repository

import com.somila.domain.model.Post

interface PostRepository {

    suspend fun getPost(id: Int): Result<Post>
    suspend fun getPosts() : Result<List<Post>>
    suspend fun createPost(postRequest: Post): Result<Post>
    suspend fun updatePost(id: Int, postRequest: Post) : Result<Post>
    suspend fun deletePost(id: Int) : Result<Unit>
}