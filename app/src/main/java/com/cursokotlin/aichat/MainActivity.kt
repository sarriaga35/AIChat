package com.cursokotlin.aichat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cursokotlin.aichat.ui.screens.ChatScreen
import com.cursokotlin.aichat.ui.theme.AIChatTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            AIChatTheme {
                var viewModel : ChatViewModel = viewModel()
                ChatScreen(viewModel = viewModel)
            }
        }
    }
}