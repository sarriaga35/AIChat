package com.cursokotlin.aichat.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cursokotlin.aichat.ui.components.ChatContent
import com.cursokotlin.aichat.ui.components.MessageInput
import com.cursokotlin.aichat.ui.theme.AIChatTheme
import com.cursokotlin.aichat.ui.viewmodels.ChatViewModel

/**
 * Main chat screen that brings together all chat components
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel = viewModel()
) {
    // State management
    var messageText by remember { mutableStateOf("") }
    val messages by viewModel.messages.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    
    Scaffold(
        topBar = { ChatTopBar() },
        bottomBar = { 
            ChatBottomBar(
                messageText = messageText,
                onMessageTextChange = { messageText = it },
                onSendMessage = { message ->
                    viewModel.sendMessage(message)
                    messageText = ""
                }
            )
        }
    ) { paddingValues ->
        // Main chat content
        ChatContent(
            messages = messages,
            isLoading = isLoading,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

/**
 * Top app bar for the chat screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopBar() {
    TopAppBar(
        title = { Text("AI Chat") },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}

/**
 * Bottom bar with message input field
 */
@Composable
fun ChatBottomBar(
    messageText: String,
    onMessageTextChange: (String) -> Unit,
    onSendMessage: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .shadow(elevation = 8.dp)
            .zIndex(1f),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp
    ) {
        MessageInput(
            value = messageText,
            onValueChange = onMessageTextChange,
            onSendMessage = onSendMessage,
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .imePadding()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    AIChatTheme {
        ChatScreen()
    }
} 