package com.example.windimessenger.presentation.screens

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import com.example.windimessenger.presentation.navigation.AuthNavGraph
import com.example.windimessenger.presentation.navigation.NavigationState
import com.example.windimessenger.presentation.navigation.Screen

@Composable
fun AuthScreen(
    navigateState: NavigationState
) {
    AuthNavGraph(
        navHostController = navigateState.navHostController,
        loginScreenContent = {
            LoginScreen { navigateState.navigateTo(Screen.SignUp) }
        },
        signUpScreenContent = {
            SignUpScreen { navigateState.navHostController.popBackStack() }
            BackHandler { navigateState.navHostController.popBackStack() }
        }
    )
}
