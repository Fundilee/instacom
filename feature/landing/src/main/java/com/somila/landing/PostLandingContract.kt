package com.somila.landing

import com.somila.domain.model.Post

object PostLandingContract {

    data class UiState(
        val loading: Boolean = false,
        val postList: List<Post> = emptyList(),
        val filteredPosts: List<Post> = emptyList(),
        val name: String = "",
        val id: Int = 0,
        val error: String = "",
        val isSuccess: Boolean = false,
        val shouldDisplayBottomSheet: Boolean = false,
        val postFilter: String = ""
    ) {
        val hasFilters: Boolean
            get() = postFilter.isNotEmpty()

        val displayPost: List<Post>
            get() = if (hasFilters) filteredPosts else postList
    }


    sealed class UserEvent {
        data class OnSearchPost(val query: String) : UserEvent()
        data class OnAddPost(val postRequest: Post) : UserEvent()
        object OnAddPostClicked : UserEvent()
        object OnClear : UserEvent()
    }
}