package com.example.windimessenger.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute

@Composable
fun AuthNavGraph(
    navHostController: NavHostController,
    loginScreenContent: @Composable () -> Unit,
    verifyScreenContent: @Composable (String) -> Unit,
    signUpScreenContent: @Composable (String) -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Login,
    ) {
        composable<Screen.Login> {
            loginScreenContent()
        }
        composable<Screen.Verify> {
            val verify: Screen.Verify = it.toRoute()
            verifyScreenContent(verify.number)
        }
        composable<Screen.SignUp> {
            val signUp: Screen.SignUp = it.toRoute()
            signUpScreenContent(signUp.number)
        }
    }
}