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

package com.keygenqt.kchat.modules.common.navigation

import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.insets.ProvideWindowInsets
import com.keygenqt.kchat.base.LocalBaseViewModel
import com.keygenqt.kchat.extensions.addChangeRouteListener
import com.keygenqt.kchat.modules.guest.ui.compose.LoginScreen
import com.keygenqt.kchat.modules.guest.ui.compose.SignUpScreen
import com.keygenqt.kchat.modules.guest.ui.compose.WelcomeScreen
import com.keygenqt.kchat.modules.guest.ui.events.LoginEvents
import com.keygenqt.kchat.modules.guest.ui.events.SignUpEvents
import com.keygenqt.kchat.modules.guest.ui.events.WelcomeEvents
import com.keygenqt.kchat.modules.guest.ui.viewModels.GuestViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalComposeUiApi
@ExperimentalCoroutinesApi
@Composable
fun GuestNavGraph(navController: NavHostController) {

    val context = LocalContext.current

    val localBaseViewModel = LocalBaseViewModel.current

    navController.addChangeRouteListener("GuestNavGraph")

    val navActions = remember(navController) {
        GuestNavActions(navController)
    }

    val viewModel: GuestViewModel = hiltViewModel()

    val showMessage: String? by localBaseViewModel.showMessage.collectAsState()

    LaunchedEffect(showMessage) {
        showMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    ProvideWindowInsets {
        NavHost(navController = navController, startDestination = GuestNavScreen.Welcome.route) {
            composable(GuestNavScreen.Welcome.route) {
                WelcomeScreen { event ->
                    when (event) {
                        is WelcomeEvents.ToLogin -> navActions.navigateToLogin.invoke()
                        is WelcomeEvents.ToRegistration -> navActions.navigateToRegistration.invoke()
                    }
                }
            }
            composable(GuestNavScreen.SignUp.route) {
                SignUpScreen(viewModel) { event ->
                    when (event) {
                        is SignUpEvents.SignUp -> viewModel.signUp(
                            event.email,
                            event.password
                        ) {
                            localBaseViewModel.startUser()
                        }
                        is SignUpEvents.NavigateBack -> navActions.upPress.invoke()
                    }
                }
            }
            composable(GuestNavScreen.Login.route) {
                LoginScreen(viewModel) { event ->
                    when (event) {
                        is LoginEvents.Login -> viewModel.login(event.email, event.password) {
                            localBaseViewModel.startUser()
                        }
                        is LoginEvents.NavigateBack -> navActions.upPress.invoke()
                        is LoginEvents.ClearViewModel -> viewModel.clear()
                    }
                }
            }
        }
    }
}


