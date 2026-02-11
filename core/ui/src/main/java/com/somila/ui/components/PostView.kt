package com.somila.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.somila.domain.model.Post
import com.somila.ui.R
import kotlin.random.Random

@Composable
fun PostView(
    editEnabled: Boolean = true,
    viewHeader: String = "",
    title: String = "",
    body: String = "",
    isDetails: Boolean = false,
    onConfirm: (Post) -> Unit = {},
    onDeletePost: () -> Unit = {},
) {

    var title by remember { mutableStateOf(title) }
    var body by remember { mutableStateOf(body) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
    ) {

        Text(
            text = viewHeader,
            style = TextStyle(fontSize = 25.sp),
            color = MaterialTheme.colorScheme.error
        )

        OutlinedTextField(
            enabled = editEnabled,
            value = title,
            onValueChange = {
                title = it
            },
            label = { Text(stringResource(R.string.title)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            singleLine = true
        )

        OutlinedTextField(
            enabled = editEnabled,
            value = body,
            onValueChange = {
                body = it
            },
            label = { Text(stringResource(R.string.body)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        )

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Button(
                enabled = editEnabled,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 2.dp),
                onClick = {
                    onConfirm(
                        Post(
                            id = Random.nextInt(),
                            title = title,
                            body = body,
                            createdAt = System.currentTimeMillis().toString(),
                            updatedAt = System.currentTimeMillis().toString()
                        )
                    )
                }
            ) {
                Text(stringResource(R.string.submit))
            }

            if (isDetails) {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 2.dp),
                    onClick = {
                        onDeletePost()
                    }
                ) {
                    Text(stringResource(R.string.delete))
                }
            }
        }
    }
}