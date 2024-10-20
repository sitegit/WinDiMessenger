package com.example.windimessenger.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChatBubble
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.windimessenger.R

sealed class NavigationItem<T>(
    val route: T,
    val titleResId: Int,
    val icon: ImageVector
) {

    data object Chats: NavigationItem<Screen.Chats>(
        route = Screen.Chats,
        titleResId = R.string.navigation_item_chats,
        icon = Icons.Outlined.ChatBubble
    )

    data object Profile: NavigationItem<Screen.Profile>(
        route = Screen.Profile,
        titleResId = R.string.navigation_item_profile,
        icon = Icons.Outlined.Person
    )
}