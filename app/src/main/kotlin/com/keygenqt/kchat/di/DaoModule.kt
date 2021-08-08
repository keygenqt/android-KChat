package com.keygenqt.kchat.di

import com.keygenqt.kchat.data.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    fun provideDaoModelSettings(appDatabase: AppDatabase) = appDatabase.daoChatModel()
}
