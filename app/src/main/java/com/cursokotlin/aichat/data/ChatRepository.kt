package com.cursokotlin.aichat.data

import com.cursokotlin.aichat.ui.viewmodels.ChatMessage
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository interface for chat operations
 */
interface ChatRepository {
    suspend fun sendMessage(userMessage: String): ChatMessage
}

/**
 * Implementation of ChatRepository that uses a ChatService
 */
@Singleton
class ChatRepositoryImpl @Inject constructor(
    private val chatService: ChatService
) : ChatRepository {
    
    override suspend fun sendMessage(userMessage: String): ChatMessage {
        // In a real app, this would call an API via the ChatService
        return chatService.generateAIResponse(userMessage)
    }
} 