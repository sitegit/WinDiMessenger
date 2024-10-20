package com.example.windimessenger.presentation.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.windimessenger.R
import com.example.windimessenger.getApplicationComponent
import com.example.windimessenger.presentation.theme.Typography
import com.example.windimessenger.presentation.theme.showToast

@Composable
fun ProfileScreen(
    paddingValues: PaddingValues,
    onEditProfileClickListener: () -> Unit,
    viewModel: ProfileViewModel = viewModel(factory = getApplicationComponent().getViewModelFactory())
) {
    val state = viewModel.profileUiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    when (val currentState = state.value) {
        is ProfileState.Error -> { showToast(context, currentState.message) }
        ProfileState.Idle -> {}
        ProfileState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                LinearProgressIndicator()
            }
        }
        is ProfileState.Success -> {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(scrollState)
            ) {

                UserInfoContent(
                    avatarSrc = currentState.data.avatarUri,
                    phone = currentState.data.phone,
                    userName = currentState.data.username,
                    city = currentState.data.city,
                    birthDay = viewModel.formatDateToClientFormat(currentState.data.birthday),
                    zodiacSign = currentState.data.getZodiacSign(),
                    status = currentState.data.status,
                )
                Spacer(modifier = Modifier.height(16.dp))

                ProfileButton(
                    text = stringResource(R.string.change_data),
                    imageVector = Icons.Outlined.Edit
                ) { onEditProfileClickListener() }
                ProfileButton(
                    text = stringResource(R.string.logout_account),
                    imageVector = Icons.AutoMirrored.Outlined.ExitToApp
                ) { viewModel.logoutApp() }
            }
        }
    }
}

@Composable
private fun UserInfoContent(
    avatarSrc: String,
    phone: String,
    userName: String,
    city: String,
    birthDay: String,
    zodiacSign: String,
    status: String
) {
    UserAvatar(avatarSrc = avatarSrc)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = stringResource(R.string.account), style = Typography.titleLarge, fontSize = 28.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = userName, style = Typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "+$phone", style = Typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
        UserInfoCard(info = city, description = stringResource(R.string.city))
        UserInfoCard(info = birthDay, description = stringResource(R.string.date_birth))
        UserInfoCard(info = zodiacSign, description = stringResource(R.string.zodiac_sign))
        UserInfoCard(info = status, description = stringResource(R.string.about), isStatus = true)
    }
}

@Composable
private fun UserInfoCard(
    info: String,
    description: String,
    isStatus: Boolean = false
) {
    val text = info.ifBlank { stringResource(R.string.information_available) }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        if (isStatus) {
            Text(text = description)
            Text(text = text)
        } else {
            Text(text = text)
            Text(text = description)
        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
    }
}

@Composable
private fun UserAvatar(
    avatarSrc: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .size(300.dp)
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = rememberAsyncImagePainter(avatarSrc),
            contentDescription = null,
            Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun ProfileButton(
    text: String,
    imageVector: ImageVector,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = text)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = imageVector,
                contentDescription = ""
            )
        }
    }
}
