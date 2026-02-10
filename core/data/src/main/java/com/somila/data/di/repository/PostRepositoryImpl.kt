package com.somila.data.di.repository

import com.somila.data.di.mapper.PostMapper
import com.somila.domain.model.Post
import com.somila.domain.repository.PostRepository
import com.somila.network.di.retrofit.ApiService

class PostRepositoryImpl(private val api: ApiService) : PostRepository {

    override suspend fun getPosts(): Result<List<Post>> {
        return try {
            val response = api.getPosts()
            if (response.isSuccessful) {
                val posts = response.body()
                if (posts != null) {
                    val postList = PostMapper.mapToDomainList(posts)
                    Result.success(postList)
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

    override suspend fun getPost(id: Int): Result<Post> {
        return try {
            val response = api.getPost(id)
            if (response.isSuccessful) {
                val post = response.body()
                if (post != null) {
                    val post = PostMapper.mapToDomain(post)
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

    override suspend fun createPost(postRequest: Post): Result<Post> {
        return try {
            val response = api.createPost(postRequest)
            if (response.isSuccessful) {
                val post = response.body()
                if (post != null) {
                    val post = PostMapper.mapToDomain(post)
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
                    val post = PostMapper.mapToDomain(post)
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