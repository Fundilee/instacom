package com.somila.instacom.ui.theme.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.somila.instacom.domain.model.Post
import com.somila.instacom.domain.model.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsLandingScreenViewModel(
    private val repository: Repository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(PostDetailsContract.UiState())
    val state: StateFlow<PostDetailsContract.UiState> = _state.asStateFlow()

    private val id = savedStateHandle.get<Int>("id") ?: 0

    init {
        loadPost()
    }

    private fun loadPost() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true, error = "", isSuccess = false) }
            repository.getPost(id = id)
                .onSuccess { post ->
                    _state.update {
                        it.copy(
                            post = post,
                            loading = false,
                            isSuccess = true
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            error = error.message ?: "Unknown error",
                            loading = false,
                            isSuccess = false
                        )
                    }
                }
        }
    }

    fun setEvent(event: PostDetailsContract.UserEvent) {
        when (event) {
            is PostDetailsContract.UserEvent.OnDeletePost -> {
                deletePost()
            }

            is PostDetailsContract.UserEvent.OnUpdatePost -> {
                updatePost(event.postRequest)

            }
        }
    }

    private fun updatePost(postRequest: Post) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true, error = "", isPostUpdateSuccess = false) }
            repository.updatePost(id = id, postRequest = postRequest)
                .onSuccess {
                    _state.update {
                        it.copy(
                            loading = false,
                            isPostUpdateSuccess = true,
                            error = ""
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            error = error.message ?: "Unknown error",
                            loading = false,
                            isPostUpdateSuccess = false
                        )
                    }
                }
        }
    }

    private fun deletePost() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true, error = "", isPostUpdateSuccess = false) }
            repository.deletePost(id = id)
                .onSuccess {
                    _state.update {
                        it.copy(
                            loading = false,
                            isPostUpdateSuccess = true,
                            error = ""
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            error = error.message ?: "Unknown error",
                            loading = false,
                            isPostUpdateSuccess = false
                        )
                    }
                }
        }
    }
}