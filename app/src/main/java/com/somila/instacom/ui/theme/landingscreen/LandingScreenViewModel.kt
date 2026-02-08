package com.somila.instacom.ui.theme.landingscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.somila.instacom.domain.model.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class LandingScreenViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableStateFlow(LandingScreenContract.UiState())
    val state: StateFlow<LandingScreenContract.UiState> = _state.asStateFlow()

    init {
        loadPostList()
    }

    private fun loadPostList() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true, error = "", isSuccess = false) }
            repository.getPosts()
                .onSuccess { responseList ->
                    _state.update {
                        it.copy(
                            postList = responseList,
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

    fun setEvent(event: LandingScreenContract.UserEvent) {
        when (event) {
            is LandingScreenContract.UserEvent.OnSearchPost -> {
                updatePostFilter(event)
            }
        }
    }

    private fun updatePostFilter(event: LandingScreenContract.UserEvent.OnSearchPost) {
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