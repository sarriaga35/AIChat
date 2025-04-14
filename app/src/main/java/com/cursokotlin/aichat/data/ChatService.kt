package com.cursokotlin.aichat.data

import com.cursokotlin.aichat.ui.viewmodels.ChatMessage
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Service that handles AI response generation
 */
@Singleton
class ChatService @Inject constructor() {
    
    /**
     * Simulates AI response generation
     * In a real app, this would call an external API
     */
    suspend fun generateAIResponse(userMessage: String): ChatMessage {
        // Simulate network delay
        delay(1000)
        
        // Generate a simple response
        val response = when {
            userMessage.contains("hello", ignoreCase = true) || 
            userMessage.contains("hi", ignoreCase = true) -> 
                "Hello! How can I assist you today?"
                
            userMessage.contains("help", ignoreCase = true) -> 
                "I'm here to help. What would you like to know?"
                
            userMessage.contains("thank", ignoreCase = true) -> 
                "You're welcome! Is there anything else I can help with?"
                
            userMessage.length < 10 -> 
                "I'd be happy to discuss more if you can provide additional details."
                
            else -> 
                "I received your message: \"$userMessage\". This is a simulated response."
        }
        
        return ChatMessage(
            content = response,
            isUserMessage = false
        )
    }
} 