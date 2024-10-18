package com.example.windimessenger.presentation.screen.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.windimessenger.getApplicationComponent
import com.example.windimessenger.presentation.theme.Typography

@Composable
fun ProfileScreen(
    paddingValues: PaddingValues,
    onEditProfileClickListener: () -> Unit,
    viewModel: ProfileViewModel = viewModel(factory = getApplicationComponent().getViewModelFactory())
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(paddingValues).statusBarsPadding()
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomEnd) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ExitToApp,
                contentDescription = "",
                modifier = Modifier.clickable {
                    viewModel.logoutApp()
                }
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.clickable {
                    onEditProfileClickListener()
                },
                text = "ProfileScreen",
                style = Typography.titleLarge
            )
        }
    }

}