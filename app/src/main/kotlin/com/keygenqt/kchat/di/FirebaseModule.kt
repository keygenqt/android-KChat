/*
 * Copyright 2021 Vitaliy Zarubin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
package com.keygenqt.kchat.di

import android.content.Context
import androidx.startup.AppInitializer
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.perf.ktx.performance
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.keygenqt.kchat.initializer.RemoteConfigInitializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    fun provideFirebaseCrashlytics() = FirebaseCrashlytics.getInstance()

    @Provides
    fun provideFirebasePerformance() = Firebase.performance

    @Provides
    fun provideFirebaseRemoteConfig() = Firebase.remoteConfig

    @Provides
    fun provideFirebaseAnalytics(
        @ApplicationContext context: Context
    ) = FirebaseAnalytics.getInstance(context)
}
