package com.cursokotlin.aichat.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cursokotlin.aichat.ChatMessage
import com.cursokotlin.aichat.ui.theme.AIChatTheme

/**
 * Displays the empty state when no messages are present
 */
@Composable
fun EmptyChatState(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Send a message to start the conversation",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
    }
}

/**
 * Displays the list of chat messages
 */
@Composable
fun ChatMessagesList(
    messages: List<com.cursokotlin.aichat.ChatMessage>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        reverseLayout = true
    ) {
        items(messages.reversed()) { message ->
            ChatMessageItem(message = message)
        }
    }
}

/**
 * Displays a loading indicator while waiting for a response
 */
@Composable
fun ChatLoadingIndicator(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.padding(16.dp)
        )
    }
}

/**
 * Displays a single chat message item (user or AI)
 */
@Composable
fun ChatMessageItem(
    message: ChatMessage,
    modifier: Modifier = Modifier
) {
    val isFromUser = message.isUserMessage
    
    // Different colors for user and AI messages
    val backgroundColor = if (isFromUser) {
        // User message - blue tone
        MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
    } else {
        // AI message - different tone (e.g., light purple/gray)
        Color(0xFFE1E0FF)  // Light purple for AI messages
    }
    
    val textColor = if (isFromUser) {
        // White text for user messages
        Color.White
    } else {
        // Dark text for AI messages
        Color(0xFF2C2C2C)
    }
    
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // For AI messages, we align to the start (left)
        if (!isFromUser) {
            Card(
                modifier = Modifier
                    .widthIn(max = 340.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 12.dp,
                            topEnd = 12.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 12.dp
                        )
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = backgroundColor
                )
            ) {
                Text(
                    text = message.content,
                    color = textColor,
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        } 
        // For user messages, we align to the end (right)
        else {
            Spacer(modifier = Modifier.weight(1f))
            Card(
                modifier = Modifier
                    .widthIn(max = 340.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 12.dp,
                            topEnd = 12.dp,
                            bottomStart = 12.dp,
                            bottomEnd = 0.dp
                        )
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = backgroundColor
                )
            ) {
                Text(
                    text = message.content,
                    color = textColor,
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

/**
 * Main content container for chat that handles switching between different states
 */
@Composable
fun ChatContent(
    messages: List<com.cursokotlin.aichat.ChatMessage>,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Show empty state when no messages and not loading
        if (messages.isEmpty() && !isLoading) {
            EmptyChatState()
        } 
        // Otherwise show the message list
        else {
            ChatMessagesList(messages = messages)
        }
        
        // Show loading indicator when loading
        if (isLoading) {
            ChatLoadingIndicator()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatComponentsPreview() {
    AIChatTheme {
        Column {
            EmptyChatState(modifier = Modifier.height(200.dp))
            ChatMessageItem(
                message = ChatMessage(
                    id = "1",
                    content = "Hello! How can I help you today?",
                    isUserMessage = false
                )
            )
            ChatMessageItem(
                message = ChatMessage(
                    id = "2",
                    content = "I have a question about my account.",
                    isUserMessage = true
                )
            )
        }
    }
} 