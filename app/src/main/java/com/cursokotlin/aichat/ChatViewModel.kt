package com.cursokotlin.aichat

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

            // Set loading state
            _isLoading.value = true
            
            try {
                // Get AI response from the repository
                val aiMessage = chatRepository.sendMessage(content)
                
                // Add AI message to the list
                val updatedMessages = _messages.value.toMutableList()
                updatedMessages.add(0, aiMessage)
                _messages.value = updatedMessages
            } catch (e: Exception) {
                // Handle error case
                val errorMessage = ChatMessage(
                    id = UUID.randomUUID().toString(),
                    content = "Error: ${e.message}",
                    isUserMessage = false
                )
                val updatedMessages = _messages.value.toMutableList()
                updatedMessages.add(0, errorMessage)
                _messages.value = updatedMessages
            } finally {
                // Set loading state to false
                _isLoading.value = false
            }
        }
    }
} 