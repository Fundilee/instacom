package com.somila.landing

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.somila.domain.model.Post
import com.somila.ui.components.BottomSheetView
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostListScreen(
    onPostClick: (Int) -> Unit = {},
    viewModel: PostLandingViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state.shouldDisplayBottomSheet) {
        BottomSheetView(
            onDismiss = {
                viewModel.setEvent(PostLandingContract.UserEvent.OnClear)
            },
            onConfirm = { post ->
                viewModel.setEvent(PostLandingContract.UserEvent.OnAddPost(post))
            })
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.available_posts)) }, actions = {
                    IconButton(
                        onClick = { viewModel.setEvent(PostLandingContract.UserEvent.OnAddPostClicked) }) {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                state.loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.Companion.align(Alignment.Center)
                    )
                }

                state.error.isNotEmpty() -> {
                    Text(
                        text = state.error,
                        modifier = Modifier.Companion.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.error
                    )
                }

                else -> {

                    Column(modifier = Modifier.fillMaxSize()) {
                        OutlinedTextField(
                            value = state.postFilter,
                            onValueChange = {
                                viewModel.setEvent(
                                    PostLandingContract.UserEvent.OnSearchPost(
                                        query = it
                                    )
                                )
                            },
                            label = { Text(stringResource(R.string.search_post)) },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = "Search"
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            singleLine = true
                        )

                        if (state.displayPost.isEmpty()) {
                            Text(
                                text = stringResource(R.string.no_post_found),
                                modifier = Modifier.Companion
                                    .align(Alignment.CenterHorizontally)
                                    .padding(16.dp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(state.displayPost) { post ->
                                    PokemonItem(
                                        post = post,
                                        onClick = { onPostClick(post.id ?: 0) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonItem(
    post: Post,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column {
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "#${post.id}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}