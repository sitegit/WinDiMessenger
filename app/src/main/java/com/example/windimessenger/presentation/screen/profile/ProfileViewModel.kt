package com.example.windimessenger.presentation.screen.profile

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.windimessenger.domain.entity.network.Result
import com.example.windimessenger.domain.entity.profile.UserRequest
import com.example.windimessenger.domain.usecase.auth.LogoutUseCase
import com.example.windimessenger.domain.usecase.profile.GetUserInfoUseCase
import com.example.windimessenger.domain.usecase.profile.UpdateUserInfoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase
) : ViewModel() {

    private val _profileUiState: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState.Idle)
    val profileUiState: StateFlow<ProfileState> = _profileUiState.asStateFlow()

    private val _updateDataUiState: MutableStateFlow<EditProfileState> = MutableStateFlow(EditProfileState.Idle)
    val updateDataUiState: StateFlow<EditProfileState> = _updateDataUiState.asStateFlow()

    init {
        getUserInfo()
    }

    fun logoutApp() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }

    fun updateUserInfo(userRequest: UserRequest) {
        _updateDataUiState.value = EditProfileState.Loading
        viewModelScope.launch {
            when (val result = updateUserInfoUseCase(userRequest)) {
                is Result.Error -> _updateDataUiState.value = EditProfileState.Error(result.message)
                is Result.Success -> _updateDataUiState.value = EditProfileState.Success
            }
        }
    }

    fun resetUpdateState() {
        _updateDataUiState.value = EditProfileState.Idle
    }

    fun encodeImageToBase64(uri: Uri, context: Context): String? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()

            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
            val imageBytes = outputStream.toByteArray()

            Base64.encodeToString(imageBytes, Base64.DEFAULT)
        } catch (e: Exception) {
            null
        }
    }

    fun formatDateToServerFormat(inputDate: String): String? {
        return try {
            val inputFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date: Date? = inputFormat.parse(inputDate)
            date?.let { outputFormat.format(it) }
        } catch (e: Exception) {
            null
        }
    }

    fun formatDateToClientFormat(inputDate: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val date: Date? = inputFormat.parse(inputDate)
            date?.let { outputFormat.format(it) } ?: ""
        } catch (e: Exception) {
            ""
        }
    }

    fun isValidDate(inputDate: String): Boolean {
        val datePattern = Regex("""\d{2}\.\d{2}\.\d{4}""")
        if (!datePattern.matches(inputDate)) {
            return false
        }

        return try {
            val inputFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            inputFormat.isLenient = false
            val date: Date? = inputFormat.parse(inputDate)
            date != null
        } catch (e: Exception) {
            false
        }
    }

    private fun getUserInfo() {
        viewModelScope.launch {
            _profileUiState.value = ProfileState.Loading

            getUserInfoUseCase().collectLatest {
                when (it) {
                    is Result.Error -> _profileUiState.value = ProfileState.Error(it.message)
                    is Result.Success -> _profileUiState.value = ProfileState.Success(it.data)
                }
            }
        }
    }
}