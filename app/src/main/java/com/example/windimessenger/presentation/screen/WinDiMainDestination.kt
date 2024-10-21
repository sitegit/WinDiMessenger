package com.example.windimessenger.presentation.screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.windimessenger.presentation.navigation.AppNavGraph
import com.example.windimessenger.presentation.navigation.NavigationItem
import com.example.windimessenger.presentation.navigation.NavigationState
import com.example.windimessenger.presentation.navigation.Screen
import com.example.windimessenger.presentation.screen.chat.ChatsScreen
import com.example.windimessenger.presentation.screen.chat.MessagesScreen
import com.example.windimessenger.presentation.screen.profile.EditProfileScreen
import com.example.windimessenger.presentation.screen.profile.ProfileScreen

@Composable
fun WinDiMainDestination(
    navigateState: NavigationState
) {
    val navBackStackEntry by navigateState.navHostController.currentBackStackEntryAsState()
    val showBottomBar by remember {
        derivedStateOf {
            navBackStackEntry?.destination?.hierarchy?.any {
                it.route == Screen.Chats::class.qualifiedName || it.route == Screen.Profile::class.qualifiedName
            } ?: false
        }
    }

    val bottomBarState = remember { MutableTransitionState(showBottomBar) }
    bottomBarState.targetState = showBottomBar

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visibleState = bottomBarState,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                AppBottomNavigation(navigateState, navBackStackEntry)
            }
        }
    ) { paddingValues ->
        AppNavGraph(
            navHostController = navigateState.navHostController,
            chatsScreenContent = {
                ChatsScreen(
                    paddingValues = paddingValues,
                    onMessagesClickListener = { navigateState.navigateTo(Screen.Messages(id = it)) }
                )
            },
            messagesScreenContent = {
                MessagesScreen(
                    chatId = it
                )
            },
            profileScreenContent = {
                ProfileScreen(
                    paddingValues = paddingValues,
                    onEditProfileClickListener = { navigateState.navigateTo(Screen.EditProfile) }
                )
            },
            editProfileScreenContent = {
                EditProfileScreen()
                BackHandler { navigateState.navigateTo(Screen.Profile) }
            }
        )
    }
}

@Composable
private fun AppBottomNavigation(
    navigateState: NavigationState,
    navBackStackEntry: NavBackStackEntry?
) {
    NavigationBar(
        modifier = Modifier.height(56.dp)
    ) {

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
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

