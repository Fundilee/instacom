package com.somila.instacom.domain.model.repository

import com.somila.instacom.network.ApiService
import com.somila.instacom.domain.model.Post

class RepositoryImpl(private val api: ApiService) : Repository {

    override suspend fun getPosts(): Result<List<Post>> {
        return try {
            val response = api.getPosts()
            if (response.isSuccessful) {
                val posts = response.body()
                if (posts != null) {
                    Result.success(posts)
                } else {
                    Result.failure(Exception("Empty response"))
                }
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createPost(postRequest: Post): Result<Post> {
        return try {
            val response = api.createPost(postRequest)
            if (response.isSuccessful) {
                val post = response.body()
                if (post != null) {
                    Result.success(post)
                } else {
                    Result.failure(Exception("Empty response"))
                }
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updatePost(id: Int, postRequest: Post): Result<Post> {
        return try {
            val response = api.updatePost(id = id, post = postRequest)
            if (response.isSuccessful) {
                val post = response.body()
                if (post != null) {
                    Result.success(post)
                } else {
                    Result.failure(Exception("Empty response"))
                }
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deletePost(id: Int): Result<Unit> {
        return try {
            val response = api.deletePost(id = id)
            if (response.isSuccessful) {
                val post = response.body()
                if (post != null) {
                    Result.success(post)
                } else {
                    Result.failure(Exception("Empty response"))
                }
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}