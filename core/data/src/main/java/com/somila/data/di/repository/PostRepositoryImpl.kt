package com.somila.data.di.repository

import com.somila.data.di.mapper.PostMapper
import com.somila.domain.model.Post
import com.somila.domain.repository.PostRepository
import com.somila.network.di.retrofit.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class PostRepositoryImpl(private val api: ApiService) : PostRepository {

    private val _posts = MutableStateFlow<List<Post>>(emptyList())

    override fun observePosts(): StateFlow<List<Post>> = _posts

    override suspend fun getPosts(): Result<Unit> {
        return try {
            val response = api.getPosts()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val posts = body.map { PostMapper.mapToDomain(it) }
                    _posts.value = posts
                    Result.success(Unit)
                } else {
                    Result.failure(Exception("Empty response"))
                }
            } else {

                Result.failure(handleApiErrorCode(response.code()))
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
                Result.failure(handleApiErrorCode(response.code()))
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
                    _posts.update { list -> list + post }
                    Result.success(post)
                } else {
                    Result.failure(Exception("Empty response"))
                }
            } else {
                Result.failure(handleApiErrorCode(response.code()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updatePost(id: Int, postRequest: Post): Result<Post> {
        return try {
            val response = api.updatePost(id, postRequest)

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    val updatedPost = PostMapper.mapToDomain(body)
                    _posts.update { list -> list.map {
                            if (it.id == updatedPost.id) updatedPost else it
                        }
                    }
                    Result.success(updatedPost)
                } else {
                    Result.failure(Exception("Empty response"))
                }

            } else {
                Result.failure(Exception("Error ${response.code()}"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deletePost(id: Int): Result<Unit> {
        return try {
            val response = api.deletePost(id = id)
            if (response.isSuccessful) {
                //Remove the post from cache
                _posts.update { list -> list.filterNot { it.id == id } }
                Result.success(Unit)
            } else {
                Result.failure(handleApiErrorCode(response.code()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun handleApiErrorCode(code: Int): Exception {
        val exception = if (code == 404) {
            Exception("Requested data not found")
        } else if (code == 429) {
            Exception("Too many requests. Please try again later.")
        } else if (code >= 500) {
            Exception("Server error occurred. Please try again later.")
        } else {
            Exception("Request failed with code")
        }
        return exception
    }
}