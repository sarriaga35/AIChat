package com.cursokotlin.aichat.di

import com.cursokotlin.aichat.data.ChatRepository
import com.cursokotlin.aichat.data.ChatRepositoryImpl
import com.cursokotlin.aichat.data.ChatService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Defines dependencies to be injected across the app
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    /**
     * Provides the ChatService instance
     */
    @Provides
    @Singleton
    fun provideChatService(): ChatService {
        return ChatService()
    }
}

/**
 * Interface bindings module
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    /**
     * Binds the ChatRepository implementation to the interface
     */
    @Binds
    @Singleton
    abstract fun bindChatRepository(
        chatRepositoryImpl: ChatRepositoryImpl
    ): ChatRepository
} 