package com.example.windimessenger.presentation.screen.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.windimessenger.domain.entity.chat.Chat
import com.example.windimessenger.getApplicationComponent
import com.example.windimessenger.presentation.theme.Typography
import com.example.windimessenger.presentation.theme.showToast

@Composable
fun MessagesScreen(
    chatId: Int,
    viewModel: ChatViewModel = viewModel(factory = getApplicationComponent().getViewModelFactory())
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val chat = viewModel.getChat(chatId)

    when (val currentState = state.value) {
        is MessageState.Error -> { showToast(context, currentState.message) }
        MessageState.Idle -> {}
        MessageState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                LinearProgressIndicator()
            }
        }
        is MessageState.Success -> {
            MessagesScreenContent(
                username = currentState.data.username,
                chat = chat
            )
        }
    }
}

@Composable
fun MessagesScreenContent(
    username: String,
    chat: Chat
) {
    val maxWidth = (LocalConfiguration.current.screenWidthDp * 0.8).dp
    val listState = rememberLazyListState()

    LaunchedEffect(chat.messages) {
        if (chat.messages.isNotEmpty()) listState.animateScrollToItem(chat.messages.size - 1)
    }

    Column(
        modifier = Modifier.fillMaxSize().imePadding(),
        verticalArrangement = Arrangement.Bottom
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(
                top = 8.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 8.dp
            ),
            state = listState
        ) {
            items(
                items = chat.messages,
                key = { it.id }
            ) { message ->
                val name = if (message.isSendByUser) username else chat.name
                MessageCard(name, message.content, message.isSendByUser, maxWidth)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = "",
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.weight(1f).padding(vertical = 8.dp, horizontal = 16.dp),
                shape = RoundedCornerShape(50.dp),
                textStyle = Typography.bodyLarge,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = null,
                modifier = Modifier.padding(end = 16.dp).size(28.dp)
            )
        }
    }
}

@Composable
fun MessageCard(
    name: String,
    message: String,
    isSendByUser: Boolean,
    maxWidth: Dp
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isSendByUser) Arrangement.End else Arrangement.Start
    ) {
        Card(
            modifier = Modifier.widthIn(max = maxWidth),
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = name, style = Typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = message,
                    style = Typography.bodyLarge
                )
            }
        }
    }
}