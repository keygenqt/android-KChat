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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.paging.ExperimentalPagingApi
import com.google.accompanist.insets.ProvideWindowInsets
import com.keygenqt.kchat.base.LocalBaseViewModel
import com.keygenqt.kchat.extensions.addChangeRouteListener
import com.keygenqt.kchat.modules.user.chat.ui.compose.listChats.ListChatsScreen
import com.keygenqt.kchat.modules.user.chat.ui.compose.viewChat.ViewChatScreen
import com.keygenqt.kchat.modules.user.chat.ui.events.ListChatsEvents
import com.keygenqt.kchat.modules.user.chat.ui.events.ViewChatEvents
import com.keygenqt.kchat.modules.user.chat.ui.viewModels.ChatViewModel
import com.keygenqt.kchat.modules.user.settings.ui.compose.SettingsScreen
import com.keygenqt.kchat.modules.user.settings.ui.events.SettingsEvents
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun UserNavGraph(navController: NavHostController) {

    val context = LocalContext.current

    val localBaseViewModel = LocalBaseViewModel.current

    navController.addChangeRouteListener("UserNavGraph")

    val navActions = remember(navController) {
        UserNavActions(navController)
    }

    val showMessage: String? by localBaseViewModel.showMessage.collectAsState()

    LaunchedEffect(showMessage) {
        showMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    ProvideWindowInsets {
        NavHost(navController = navController, startDestination = UserNavScreen.ListChats.route) {
            composable(UserNavScreen.ListChats.route) {

                val viewModel: ChatViewModel = hiltViewModel()

                ListChatsScreen(viewModel = viewModel) { event ->
                    when (event) {
                        is ListChatsEvents.Search -> viewModel.search(event.text)
                        is ListChatsEvents.ToChatView -> navActions.navigateToViewChat(event.id)
                        is ListChatsEvents.ToSettings -> navActions.navigateToSettings()
                        is ListChatsEvents.CreateChat -> viewModel.createChat(name = event.name)
                        is ListChatsEvents.Logout -> localBaseViewModel.logout()
                    }
                }
            }
            composable(UserNavScreen.Settings.route) {
                SettingsScreen(viewModel = hiltViewModel()) { event ->
                    when (event) {
                        is SettingsEvents.NavigateBack -> navActions.upPress()
                    }
                }
            }
            composable(
                route = UserNavScreen.ViewChat.routeWithArgument,
                arguments = listOf(
                    navArgument(UserNavScreen.ViewChat.argument0) { type = NavType.IntType },
                )
            ) { backStackEntry ->
                backStackEntry.arguments?.let {
                    ViewChatScreen(
                        id = it.getInt(UserNavScreen.ViewChat.argument0),
                        viewModel = hiltViewModel()
                    ) { event ->
                        when (event) {
                            is ViewChatEvents.NavigateBack -> navActions.upPress()
                        }
                    }
                }
            }
        }
    }
}


