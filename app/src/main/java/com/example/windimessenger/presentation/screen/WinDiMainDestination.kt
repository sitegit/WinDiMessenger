package com.example.windimessenger.presentation.screen

import androidx.activity.compose.BackHandler
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.windimessenger.presentation.navigation.AppNavGraph
import com.example.windimessenger.presentation.navigation.NavigationItem
import com.example.windimessenger.presentation.navigation.NavigationState
import com.example.windimessenger.presentation.navigation.Screen
import com.example.windimessenger.presentation.screen.chat.ChatsScreen
import com.example.windimessenger.presentation.screen.profile.EditProfileScreen
import com.example.windimessenger.presentation.screen.chat.MessagesScreen
import com.example.windimessenger.presentation.screen.profile.ProfileScreen

@Composable
fun WinDiMainDestination(
    navigateState: NavigationState
) {
    Scaffold(
        bottomBar = { AppBottomNavigation(navigateState) }
    ) { paddingValues ->
        AppNavGraph(
            navHostController = navigateState.navHostController,
            chatsScreenContent = {
                ChatsScreen { navigateState.navigateTo(Screen.Messages) }
            },
            messagesScreenContent = {
                MessagesScreen {  }
            },
            profileScreenContent = {
                ProfileScreen(
                    onEditProfileClickListener = { navigateState.navigateTo(Screen.EditProfile) }
                )
            },
            editProfileScreenContent = {
                EditProfileScreen { }
                BackHandler { navigateState.navigateTo(Screen.Profile) }
            }
        )
    }
}

@Composable
private fun AppBottomNavigation(
    navigateState: NavigationState
) {
    NavigationBar {
        val navBackStackEntry by navigateState.navHostController.currentBackStackEntryAsState()

        val items = listOf(
            NavigationItem.Chats,
            NavigationItem.Profile
        )

        items.forEach { item ->

            val isSelected = navBackStackEntry?.destination?.hierarchy?.any {
                it.route == item.route::class.qualifiedName
            } ?: false

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navigateState.navigateTo(item.route)
                },
                icon = {
                    Icon(item.icon, contentDescription = null)
                },
                label = {
                    Text(text = stringResource(id = item.titleResId))
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSecondary,
                    unselectedTextColor = MaterialTheme.colorScheme.onSecondary,
                    indicatorColor = MaterialTheme.colorScheme.secondary
                )
            )
        }
    }
}

