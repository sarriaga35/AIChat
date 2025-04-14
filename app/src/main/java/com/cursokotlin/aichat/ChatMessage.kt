package com.cursokotlin.aichat

import java.util.Date

data class ChatMessage(
    val id: String,
    val content: String,
    val isUserMessage: Boolean,
    val timestamp: Date = Date()
) 