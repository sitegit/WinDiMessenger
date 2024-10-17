package com.example.windimessenger.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {

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
    data class Verify(val number: String) : Screen()

    @Serializable
    data class SignUp(val number: String) : Screen()
}


