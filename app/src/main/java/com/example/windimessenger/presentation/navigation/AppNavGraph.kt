package com.example.windimessenger.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    chatsScreenContent: @Composable () -> Unit,
    editProfileScreenContent: @Composable () -> Unit,
    messagesScreenContent: @Composable (Int) -> Unit,
    profileScreenContent: @Composable () -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Chats,
    ) {

        composable<Screen.Chats> {
            chatsScreenContent()
        }
        composable<Screen.Messages> {
            val chat: Screen.Messages = it.toRoute()
            messagesScreenContent(chat.id)
        }

        composable<Screen.Profile> {
            profileScreenContent()
        }
        composable<Screen.EditProfile> {
            editProfileScreenContent()
        }
    }
}