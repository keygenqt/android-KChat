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

import androidx.navigation.NavHostController
import com.keygenqt.kchat.base.NavActions

class GuestNavActions(controller: NavHostController) : NavActions(controller) {
    val navigateToLogin: () -> Unit = {
        controller.navigate(GuestNavScreen.Login.route)
    }
    val navigateToRegistration: () -> Unit = {
        controller.navigate(GuestNavScreen.SignUp.route)
    }
}