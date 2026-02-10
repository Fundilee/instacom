package com.somila.instacom.domain.model.network

import com.somila.instacom.domain.model.Post
import com.somila.instacom.domain.model.Posts
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("posts")
    suspend fun getPosts(): Response<Posts>

    @GET("post/{id}")
    suspend fun getPost(@Path("id") id: Int): Response<Post>

    @POST("/post")
    suspend fun createPost(@Body post: Post): Response<Post>

    @PUT("post/{id}")
    suspend fun updatePost(
        @Path("id") id: Int,
        @Body post: Post
    ): Response<Post>

    @DELETE("post/{id}")
    suspend fun deletePost(@Path("id") id: Int): Response<Unit>
}