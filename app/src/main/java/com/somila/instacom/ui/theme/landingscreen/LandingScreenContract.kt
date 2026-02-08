package com.somila.instacom.ui.theme.landingscreen

import com.somila.instacom.domain.model.Post

object LandingScreenContract {

    data class UiState(
        val loading: Boolean = false,
        val postList: List<Post> = emptyList(),
        val filteredPosts: List<Post> = emptyList(),
        val name: String = "",
        val id: Int = 0,
        val error: String = "",
        val isSuccess: Boolean = false,
        val postFilter: String = ""
    ) {
        val hasFilters: Boolean
            get() = postFilter.isNotEmpty()

        val displayPost: List<Post>
            get() = if (hasFilters) filteredPosts else postList
    }


    sealed class UserEvent {
        data class OnSearchPost(val query: String) : UserEvent()
    }
}