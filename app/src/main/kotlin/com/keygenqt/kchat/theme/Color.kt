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
 
package com.keygenqt.kchat.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.keygenqt.kchat.extensions.toColor
import com.keygenqt.kchat.utils.ConstantsRemoteConfig.PALETTE_DARK_COLOR
import com.keygenqt.kchat.utils.ConstantsRemoteConfig.PALETTE_LIGHT_COLOR

class CustomColors(
    val splash: Color,
    val isLight: Boolean
)

fun parseRemoteConfigCustomPalette(isLight: Boolean): CustomColors {

    val colors: Map<String, String> = Gson().fromJson(
        Firebase.remoteConfig.getString(if (isLight) PALETTE_LIGHT_COLOR else PALETTE_DARK_COLOR),
        object : TypeToken<HashMap<String, String>>() {}.type
    )

    val splash by colors

    return CustomColors(
        splash = splash.toColor(),
        isLight = isLight,
    )
}

fun parseRemoteConfigPalette(isLight: Boolean): Colors {

    val colors: Map<String, String> = Gson().fromJson(
        Firebase.remoteConfig.getString(if (isLight) PALETTE_LIGHT_COLOR else PALETTE_DARK_COLOR),
        object : TypeToken<HashMap<String, String>>() {}.type
    )

    val primary by colors
    val primaryVariant by colors
    val secondary by colors
    val secondaryVariant by colors
    val background by colors
    val surface by colors
    val error by colors
    val onPrimary by colors
    val onSecondary by colors
    val onBackground by colors
    val onSurface by colors
    val onError by colors

    return Colors(
        primary = primary.toColor(),
        primaryVariant = primaryVariant.toColor(),
        secondary = secondary.toColor(),
        secondaryVariant = secondaryVariant.toColor(),
        background = background.toColor(),
        surface = surface.toColor(),
        error = error.toColor(),
        onPrimary = onPrimary.toColor(),
        onSecondary = onSecondary.toColor(),
        onBackground = onBackground.toColor(),
        onSurface = onSurface.toColor(),
        onError = onError.toColor(),
        isLight = isLight,
    )
}