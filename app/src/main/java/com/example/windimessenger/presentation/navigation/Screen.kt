package com.example.windimessenger.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {

    @Serializable
    data object Home : Screen()

    @Serializable
    data object Chats : Screen()

    @Serializable
    data object EditProfile : Screen()

    @Serializable
    data object Messages : Screen()

    @Serializable
    data object Profile : Screen()

    @Serializable
    data object Login : Screen()

    @Serializable
    data object SignUp : Screen()
}


