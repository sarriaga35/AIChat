package com.cursokotlin.aichat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor() : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun sendMessage(content: String) {
        if (content.isBlank()) return

        // Create and add user message
        val userMessage = ChatMessage(
            id = UUID.randomUUID().toString(),
            content = content,
            isUserMessage = true
        )
        
        // Update messages list with user message
        val currentMessages = _messages.value.toMutableList()
        currentMessages.add(0, userMessage)
        _messages.value = currentMessages

        // Generate AI response
        generateAiResponse(content)
    }

    private fun generateAiResponse(userMessage: String) {
        viewModelScope.launch {
            _isLoading.value = true
            
            // Simulate network delay for AI response
            delay(1500)
            
            // For now, just create a simple echo response
            val aiResponseContent = "This is a simulated AI response to: $userMessage"
            val aiMessage = ChatMessage(
                id = UUID.randomUUID().toString(),
                content = aiResponseContent,
                isUserMessage = false
            )
            
            // Add AI message to the list
            val currentMessages = _messages.value.toMutableList()
            currentMessages.add(0, aiMessage)
            _messages.value = currentMessages
            
            _isLoading.value = false
        }
    }
} 