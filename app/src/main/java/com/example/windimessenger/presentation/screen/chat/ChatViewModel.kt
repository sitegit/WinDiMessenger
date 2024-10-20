package com.example.windimessenger.presentation.screen.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.windimessenger.domain.entity.chat.Chat
import com.example.windimessenger.domain.entity.network.Result
import com.example.windimessenger.domain.usecase.chat.GetChatListUseCase
import com.example.windimessenger.domain.usecase.chat.GetChatUseCase
import com.example.windimessenger.domain.usecase.profile.GetUserInfoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChatViewModel @Inject constructor(
    private val getChatListUseCase: GetChatListUseCase,
    private val getChatUseCase: GetChatUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<MessageState> = MutableStateFlow(MessageState.Idle)
    val uiState: StateFlow<MessageState> = _uiState.asStateFlow()

    init {
        getUserInfo()
    }

    val chats = getChatListUseCase()

    private fun getUserInfo() {
        viewModelScope.launch {
            _uiState.value = MessageState.Loading

            getUserInfoUseCase().collectLatest {
                when (it) {
                    is Result.Error -> _uiState.value = MessageState.Error(it.message)
                    is Result.Success -> _uiState.value = MessageState.Success(it.data)
                }
            }
        }
    }

    fun getChat(chatId: Int): Chat {
        return getChatUseCase(chatId)
    }
}