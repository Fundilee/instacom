package com.somila.instacom

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.somila.instacom.domain.model.Post
import com.somila.instacom.domain.model.repository.Repository
import com.somila.instacom.ui.theme.landingscreen.LandingScreenViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    private val mockRepository = mockk<Repository>()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should load posts list successfully`() = runTest(testDispatcher) {
        // Given
        val postList = listOf(
            Post(id = 1, title = "pikachu", body = "The news are as follows", userId = 12),
            Post(id = 2, title = "charizard", body = "Please ensure post is not null", userId = 12)
        )

        // When
        coEvery { mockRepository.getPosts() } returns Result.success(postList)
        val viewModel = LandingScreenViewModel(mockRepository)
        advanceUntilIdle()

        // Then
        val currentState = viewModel.state.value
        assertFalse(currentState.loading)
        assertTrue(currentState.isSuccess)
        assertEquals("", currentState.error)
        assertEquals(postList, currentState.postList)
    }

    @Test
    fun `init should load posts list unsuccessfully`() = runTest(testDispatcher) {
        // Given
        val errorMessage = "Error occurred"

        // When
        coEvery { mockRepository.getPosts() } returns Result.failure(Exception(errorMessage))
        val viewModel = LandingScreenViewModel(mockRepository)
        advanceUntilIdle()

        // Then
        val currentState = viewModel.state.value
        assertFalse(currentState.loading)
        assertFalse(currentState.isSuccess)
        assertEquals(currentState.error, errorMessage)
    }

}