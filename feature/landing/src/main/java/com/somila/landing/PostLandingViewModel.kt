package com.somila.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.somila.domain.model.Post
import com.somila.domain.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostLandingViewModel(
    private val repository: PostRepository
) : ViewModel() {

    private val _state = MutableStateFlow(PostLandingContract.UiState())
    val state: StateFlow<PostLandingContract.UiState> = _state.asStateFlow()


    init {
        observePosts()
        loadPostList()
    }

    private fun observePosts() {
        viewModelScope.launch {
            repository.observePosts().collect { resultList ->
                _state.update {
                    it.copy(
                        postList = resultList,
                    )
                }
            }
        }
    }

    private fun loadPostList() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true, error = "", isSuccess = false) }
            repository.getPosts()
                .onSuccess {
                    _state.update {
                        it.copy(
                            loading = false,
                            isSuccess = true,
                            error = ""
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

    fun setEvent(event: PostLandingContract.UserEvent) {
        when (event) {
            is PostLandingContract.UserEvent.OnSearchPost -> {
                updatePostFilter(event)
            }

            is PostLandingContract.UserEvent.OnAddPostClicked -> {
                _state.update { it.copy(shouldDisplayBottomSheet = true) }
            }

            is PostLandingContract.UserEvent.OnAddPost -> {
                addPost(event.postRequest)
            }

            is PostLandingContract.UserEvent.OnClear -> {
                _state.update { it.copy(shouldDisplayBottomSheet = false) }

            }
        }
    }

    private fun addPost(postRequest: Post) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true, error = "", isSuccess = false) }
            repository.createPost(postRequest = postRequest)
                .onSuccess {
                    _state.update {
                        it.copy(
                            loading = false,
                            isSuccess = true,
                            shouldDisplayBottomSheet = false,
                            error = ""
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            error = error.message ?: "Unknown error",
                            loading = false,
                            shouldDisplayBottomSheet = false,
                            isSuccess = false
                        )
                    }
                }
        }
    }

    private fun updatePostFilter(event: PostLandingContract.UserEvent.OnSearchPost) {
        val query = event.query.trim()
        _state.update { currentState ->
            val filteredList = if (query.isEmpty()) {
                emptyList()
            } else {
                currentState.postList.filter { post ->
                    post.title.contains(query, ignoreCase = true) ||
                            post.id.toString().contains(query)
                }
            }
            currentState.copy(
                postFilter = query,
                filteredPosts = filteredList
            )
        }
    }
}