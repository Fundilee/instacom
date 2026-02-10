package com.somila.domain.model

data class Post(
    val userId: Int,
    val id: Int? = null,
    val title: String,
    val body: String,
    val createdAt: String,
    val updatedAt: String
)