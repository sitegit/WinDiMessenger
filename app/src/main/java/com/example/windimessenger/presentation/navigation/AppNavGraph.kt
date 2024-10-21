package com.example.windimessenger.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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

        composable<Screen.Chats>(
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) }
        ) {
            chatsScreenContent()
        }
        composable<Screen.Messages>(
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) }
        ) {
            val chat: Screen.Messages = it.toRoute()
            messagesScreenContent(chat.id)
        }

        composable<Screen.Profile>(
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) }
        ) {
            profileScreenContent()
        }
        composable<Screen.EditProfile>(
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) }
        ) {
            editProfileScreenContent()
        }
    }
}