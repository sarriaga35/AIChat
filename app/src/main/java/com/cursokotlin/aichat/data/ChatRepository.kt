package com.cursokotlin.aichat.data

import com.cursokotlin.aichat.ChatMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository interface for chat operations
 */
interface ChatRepository {
    suspend fun sendMessage(userMessage: String): ChatMessage
    suspend fun getConversationHistory(): List<Message>
}

/**
 * Implementation of ChatRepository that uses OpenAI API
 */
@Singleton
class ChatRepositoryImpl @Inject constructor(
    private val openAIService: OpenAIService
) : ChatRepository {
    
    private val conversationHistory = mutableListOf<Message>()
    
    init {
        // Initialize with system prompt
        conversationHistory.add(Message(
            role = Constants.ROLE_SYSTEM,
            content = Constants.SYSTEM_PROMPT
        ))
    }
    
    override suspend fun getConversationHistory(): List<Message> {
        return conversationHistory.toList()
    }
    
    override suspend fun sendMessage(userMessage: String): ChatMessage {
        // Add user message to conversation history
        val userMessageForAPI = Message(
            role = Constants.ROLE_USER,
            content = userMessage
        )
        conversationHistory.add(userMessageForAPI)
        
        return withContext(Dispatchers.IO) {
            try {
                // Create request for OpenAI API
                val request = OpenAIRequest(
                    messages = conversationHistory
                )
                
                // Make API call
                val authHeader = "Bearer ${Constants.OPENAI_API_KEY}"
                val response = openAIService.getChatCompletion(authHeader, request)
                
                if (response.isSuccessful && response.body() != null) {
                    val openAIResponse = response.body()!!
                    val aiMessage = openAIResponse.choices.firstOrNull()?.message
                    
                    if (aiMessage != null) {
                        // Add AI response to conversation history
                        conversationHistory.add(aiMessage)
                        
                        // Convert to UI message
                        return@withContext ChatMessage(
                            id = UUID.randomUUID().toString(),
                            content = aiMessage.content,
                            isUserMessage = false
                        )
                    } else {
                        return@withContext createErrorMessage("No response from AI")
                    }
                } else {
                    return@withContext createErrorMessage("API Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                return@withContext createErrorMessage("Error: ${e.message}")
            }
        }
    }
    
    private fun createErrorMessage(errorText: String): ChatMessage {
        return ChatMessage(
            id = UUID.randomUUID().toString(),
            content = errorText,
            isUserMessage = false
        )
    }
} 