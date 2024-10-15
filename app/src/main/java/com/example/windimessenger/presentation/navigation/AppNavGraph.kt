package com.example.windimessenger.presentation.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    chatsScreenContent: @Composable () -> Unit,
    editProfileScreenContent: @Composable () -> Unit,
    messagesScreenContent: @Composable () -> Unit,
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
            messagesScreenContent()
        }

        composable<Screen.Profile> {
            profileScreenContent()
        }
        composable<Screen.EditProfile> {
            editProfileScreenContent()
        }
    }
}