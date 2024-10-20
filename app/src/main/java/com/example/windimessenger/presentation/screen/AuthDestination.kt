package com.example.windimessenger.presentation.screen

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import com.example.windimessenger.presentation.navigation.AuthNavGraph
import com.example.windimessenger.presentation.navigation.NavigationState
import com.example.windimessenger.presentation.navigation.Screen
import com.example.windimessenger.presentation.screen.login.LoginScreen
import com.example.windimessenger.presentation.screen.login.VerifyScreen
import com.example.windimessenger.presentation.screen.signup.SignUpScreen

@Composable
fun AuthDestination(
    navigateState: NavigationState
) {
    AuthNavGraph(
        navHostController = navigateState.navHostController,
        loginScreenContent = {
            LoginScreen { navigateState.navigateTo(Screen.Verify(it)) }
        },
        verifyScreenContent = {
            VerifyScreen(phoneNumber = it) { navigateState.navigateTo(Screen.SignUp(it), false) }
            BackHandler { navigateState.navHostController.popBackStack() }
        },
        signUpScreenContent = {
            SignUpScreen(phoneNumber = it)
            BackHandler { navigateState.navHostController.popBackStack() }
        }
    )
}
