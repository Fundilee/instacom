package com.somila.instacom.navigation

import kotlinx.serialization.Serializable

sealed class Destination {

    @Serializable
    data object LandingScreen
    
    @Serializable
    data class PostDetailScreen(val pokemonId: Int)
}