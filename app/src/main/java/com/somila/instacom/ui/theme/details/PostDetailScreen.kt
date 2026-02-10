package com.somila.instacom.ui.theme.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.somila.instacom.R
import com.somila.instacom.ui.theme.PostView
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailScreen(
    onNavigateBack: () -> Unit = {},
    viewModel: DetailsLandingScreenViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.post_details)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                state.loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.error.isNotEmpty() -> {
                    Text(
                        text = state.error,
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.error
                    )
                }

                state.isPostUpdateSuccess -> {
                    onNavigateBack()
                }

                else ->

                    PostView(
                        isDetails = true,
                        title = state.post?.title.orEmpty(),
                        body = state.post?.body.orEmpty(),
                        onConfirm = { post ->
                            viewModel.setEvent(
                                PostDetailsContract.UserEvent.OnUpdatePost(
                                    postRequest = post
                                )
                            )
                        },
                        onDeletePost = {
                            viewModel.setEvent(PostDetailsContract.UserEvent.OnDeletePost)
                        })
            }
        }
    }

}