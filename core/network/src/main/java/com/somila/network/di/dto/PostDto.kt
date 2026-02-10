package com.somila.network.di.dto

import com.google.gson.annotations.SerializedName

data class PostDto(
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val body: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)
