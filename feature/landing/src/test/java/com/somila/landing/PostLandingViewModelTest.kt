package com.somila.landing

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.somila.domain.model.Post
import com.somila.domain.repository.PostRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeScreenViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testScheduler = TestCoroutineScheduler()
    private val testDispatcher = StandardTestDispatcher(testScheduler)

    private val mockRepository = mockk<PostRepository>()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should load posts list successfully`() = runTest {
        // Given
        val postList = listOf(
            Post(
                id = 1,
                title = "pikachu",
                body = "The news are as follows",
                createdAt = "120.0",
                updatedAt = "120.0"
            ),
            Post(
                id = 2,
                title = "charizard",
                body = "Please ensure post is not null",
                createdAt = "120.0",
                updatedAt = "120.0"
            )
        )

        val postsFlow = MutableStateFlow(postList)
        every { mockRepository.observePosts() } returns postsFlow

        coEvery { mockRepository.getPosts() } returns Result.success(Unit)

        val viewModel = PostLandingViewModel(mockRepository)
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertFalse(state.loading)
        assertTrue(state.error.isEmpty())
        assertEquals(postList, state.postList)
    }

    @Test
    fun `init should load posts list unsuccessfully`() = runTest {
        // Given
        val errorMessage = "Error occurred"

        val postsFlow = MutableStateFlow<List<Post>>(emptyList())
        every { mockRepository.observePosts() } returns postsFlow

        coEvery { mockRepository.getPosts() } returns Result.failure(Exception(errorMessage))

        // When
        val viewModel = PostLandingViewModel(mockRepository)
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertFalse(state.loading)
        assertEquals(errorMessage, state.error)
        assertTrue(state.postList.isEmpty())
    }
}