package com.somila.instacom.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.somila.instacom.ui.theme.details.PostDetailScreen
import com.somila.instacom.ui.theme.landingscreen.PostListScreen

@Composable
fun MainNavGraph(navController: NavHostController, innerPadding: PaddingValues) {

    NavHost(navController = navController, startDestination = Destination.LandingScreen) {
        composable<Destination.LandingScreen> {
            PostListScreen(
                onPostClick = { postId ->
                    navController.navigate(Destination.PostDetailScreen(postId))
                }
            )
        }

        composable<Destination.PostDetailScreen> {
            PostDetailScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}