package com.keygenqt.kchat.di

import com.keygenqt.kchat.base.RestHttpClient
import com.keygenqt.kchat.modules.user.chat.services.ApiServiceChat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.ktor.client.*

@Module
@InstallIn(ViewModelComponent::class)
object ApiServicesModule {
    @Provides
    fun provideRepositoryForum(@RestHttpClient httpClient: HttpClient) = ApiServiceChat(httpClient)
}
