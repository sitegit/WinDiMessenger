package com.example.windimessenger.presentation.screen.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.windimessenger.domain.entity.chat.Chat
import com.example.windimessenger.getApplicationComponent
import com.example.windimessenger.presentation.theme.Typography

@Composable
fun ChatsScreen(
    paddingValues: PaddingValues,
    onMessagesClickListener: (Int) -> Unit,
    viewModel: ChatViewModel = viewModel(factory = getApplicationComponent().getViewModelFactory())
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(
            top = 8.dp,
            start = 16.dp,
            end = 16.dp,
            bottom = 8.dp
        )
    ) {
        items(
            items = viewModel.chats,
            key = { it.id }
        ) { chat ->
            val name = chat.name
            val lastChatMessage = chat.messages.last().content

            Card(
                modifier = Modifier.clickable { onMessagesClickListener(chat.id) },
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                ) {
                    Text(text = name, style = Typography.titleLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = lastChatMessage,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = Typography.bodyLarge
                    )
                }
            }
        }
    }
}