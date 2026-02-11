package com.somila.network.di.dto

data class PostDto(
    val id: Int? = null,
    val title: String,
    val body: String,
    val createdAt: String,
    val updatedAt: String
)
