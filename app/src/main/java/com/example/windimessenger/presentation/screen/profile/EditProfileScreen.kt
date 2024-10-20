package com.example.windimessenger.presentation.screen.profile

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.windimessenger.R
import com.example.windimessenger.domain.entity.profile.UserInfo
import com.example.windimessenger.domain.entity.profile.UserRequest
import com.example.windimessenger.getApplicationComponent
import com.example.windimessenger.presentation.theme.Typography
import com.example.windimessenger.presentation.theme.showToast

@Composable
fun EditProfileScreen(
    paddingValues: PaddingValues,
    viewModel: ProfileViewModel = viewModel(factory = getApplicationComponent().getViewModelFactory())
) {
    val context = LocalContext.current
    val state = viewModel.profileUiState.collectAsStateWithLifecycle()
    val updateDataState by viewModel.updateDataUiState.collectAsStateWithLifecycle()

    when (val currentState = state.value) {
        is ProfileState.Error -> { showToast(context, currentState.message) }
        ProfileState.Idle -> {}
        ProfileState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                LinearProgressIndicator()
            }
        }
        is ProfileState.Success -> {
            EditScreenContent(context, currentState.data, viewModel, paddingValues)
        }
    }

    when (val currentState = updateDataState) {
        is EditProfileState.Error -> {
            showToast(context, currentState.message)
            viewModel.resetUpdateState()
        }
        EditProfileState.Success -> {
            showToast(context, stringResource(R.string.update_data))
            viewModel.resetUpdateState()
        }
        else -> {}
    }
}

@Composable
fun EditScreenContent(
    context: Context,
    user: UserInfo,
    viewModel: ProfileViewModel,
    paddingValues: PaddingValues
) {
    var fileName by rememberSaveable { mutableStateOf("") }
    var imageBase64 by rememberSaveable { mutableStateOf("") }
    var imageUrl by rememberSaveable { mutableStateOf(user.avatarUrl) }
    var birthday by rememberSaveable {
        mutableStateOf(viewModel.formatDateToClientFormat(user.birthday))
    }
    var city by rememberSaveable { mutableStateOf(user.city) }
    var status by rememberSaveable { mutableStateOf(user.status) }
    var isValidBirthday by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .imePadding()
    ) {
        ImagePicker(
            imageUrl = imageUrl,
            onImagePicked = { uri ->
                imageUrl = uri.toString()
                imageBase64 = viewModel.encodeImageToBase64(uri, context) ?: ""
                fileName = uri.lastPathSegment ?: context.getString(R.string.avatar_jpg)
            }
        )
        EditTextField(
            text = birthday,
            label = stringResource(R.string.birthday),
            placeholder = stringResource(R.string.date)
        ) {
            isValidBirthday = if (it.isNotEmpty()) viewModel.isValidDate(it) else true
            birthday = it
        }
        EditTextField(
            text = city, label = stringResource(R.string.city_label),
            placeholder = stringResource(R.string.city_placeholder)
        ) { city = it }
        EditTextField(
            text = status, label = stringResource(R.string.about_label),
            placeholder = stringResource(R.string.about_placeholder)
        ) { status = it }

        ConfirmButton {
            if (isValidBirthday) {
                val formattedBirthday = viewModel.formatDateToServerFormat(birthday)
                if (formattedBirthday != null || birthday.isEmpty()) {
                    viewModel.updateUserInfo(
                        UserRequest(
                            avatarUrl = imageBase64,
                            avatarUri = imageUrl,
                            fileName = fileName,
                            name = user.name,
                            birthday = formattedBirthday ?: "",
                            city = city,
                            status = status,
                            username = user.username,
                            phone = user.phone
                        )
                    )
                } else {
                    showToast(context, context.getString(R.string.Incorrect_data))
                }
            } else {
                showToast(context, context.getString(R.string.Incorrect_data))
            }
        }
    }
}

@Composable
private fun ConfirmButton(
    onUpdateData: () -> Unit
) {
    Button(
        onClick = { onUpdateData() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp, horizontal = 16.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(text = stringResource(R.string.apply_changes))
    }
}

@Composable
private fun EditTextField(
    text: String,
    label: String,
    placeholder: String,
    onValueChange: (String) -> Unit
) {
    Spacer(modifier = Modifier.padding(top = 16.dp, bottom = 4.dp))
    Text(text = label, modifier = Modifier.padding(start = 16.dp))
    TextField(
        value = text,
        onValueChange = { onValueChange(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(),
        shape = RoundedCornerShape(0.dp),
        placeholder = { Text(text = placeholder) },
        textStyle = Typography.bodyLarge,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
private fun ImagePicker(
    imageUrl: String,
    onImagePicked: (Uri) -> Unit
) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val contentResolver = context.contentResolver
            contentResolver.takePersistableUriPermission(
                it, Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            onImagePicked(it)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.3f)
            .background(Color.LightGray)
            .clickable { launcher.launch("image/*") },
        contentAlignment = Alignment.Center
    ) {
        if (imageUrl.isNotBlank()) {
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Text(stringResource(R.string.selectImage))
        }
    }
}