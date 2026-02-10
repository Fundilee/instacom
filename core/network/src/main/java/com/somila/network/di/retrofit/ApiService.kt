package com.somila.network.di.retrofit

import com.somila.domain.model.Post
import com.somila.network.di.dto.PostDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("posts")
    suspend fun getPosts(): Response<List<PostDto>>

    @GET("post/{id}")
    suspend fun getPost(@Path("id") id: Int): Response<PostDto>

    @POST("/post")
    suspend fun createPost(@Body post: Post): Response<PostDto>

    @PUT("post/{id}")
    suspend fun updatePost(
        @Path("id") id: Int,
        @Body post: Post
    ): Response<PostDto>

    @DELETE("post/{id}")
    suspend fun deletePost(@Path("id") id: Int): Response<Unit>
}