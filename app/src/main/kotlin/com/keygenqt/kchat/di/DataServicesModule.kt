package com.keygenqt.kchat.di

import com.keygenqt.kchat.base.AppSharedPreferences
import com.keygenqt.kchat.data.AppDatabase
import com.keygenqt.kchat.modules.user.chat.services.DataServiceChat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object DataServicesModule {
    @Provides
    fun provideDataServiceChat(db: AppDatabase, preferences: AppSharedPreferences) =
        DataServiceChat(db, preferences)
}
