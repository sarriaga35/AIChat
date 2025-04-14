package com.cursokotlin.aichat.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cursokotlin.aichat.data.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val content: String,
    val isUserMessage: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {
    
    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    fun sendMessage(content: String) {
        if (content.isBlank()) return
        
        viewModelScope.launch {
            // Add user message
            val userMessage = ChatMessage(
                content = content,
                isUserMessage = true
            )
            
            // Update messages list with user message
            _messages.value = _messages.value + userMessage
            
            // Set loading state to true
            _isLoading.value = true
            
            try {
                // Get AI response using repository
                val aiResponse = chatRepository.sendMessage(content)
                
                // Add AI message
                _messages.value = _messages.value + aiResponse
            } catch (e: Exception) {
                // Handle error case
                val errorMessage = ChatMessage(
                    content = "Sorry, there was an error processing your request.",
                    isUserMessage = false
                )
                _messages.value = _messages.value + errorMessage
            } finally {
                // Set loading state to false
                _isLoading.value = false
            }
        }
    }
} 