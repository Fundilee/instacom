package com.somila.instacom.ui.theme.details

import com.somila.instacom.domain.model.Post

object PostDetailsContract {

    data class UiState(
        val loading: Boolean = false,
        val post: Post? = null,
        val id: Int = 0,
        val error: String = "",
        val isSuccess: Boolean = false,
        val isPostUpdateSuccess: Boolean = false,
    )


    sealed class UserEvent {
        data class OnUpdatePost(val postRequest: Post) : UserEvent()
        object OnDeletePost : UserEvent()
    }
}