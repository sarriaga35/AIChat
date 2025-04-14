package com.cursokotlin.aichat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.cursokotlin.aichat.ui.screens.ChatScreen
import com.cursokotlin.aichat.ui.theme.AIChatTheme
import com.cursokotlin.aichat.ui.viewmodels.ChatViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val viewModel: ChatViewModel by viewModels()
        
        setContent {
            AIChatTheme {
                ChatScreen(viewModel = viewModel)
            }
        }
    }
}