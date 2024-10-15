package com.example.windimessenger.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AuthNavGraph(
    navHostController: NavHostController,
    loginScreenContent: @Composable () -> Unit,
    signUpScreenContent: @Composable () -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Login,
    ) {
        composable<Screen.Login> {
            loginScreenContent()
        }
        composable<Screen.SignUp> {
            signUpScreenContent()
        }
    }
}