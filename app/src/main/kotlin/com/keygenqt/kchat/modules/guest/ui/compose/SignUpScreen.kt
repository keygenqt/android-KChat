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
 
package com.keygenqt.kchat.modules.guest.ui.compose

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keygenqt.kchat.R
import com.keygenqt.kchat.modules.common.ui.compose.FormError
import com.keygenqt.kchat.modules.common.ui.compose.MainScaffold
import com.keygenqt.kchat.modules.common.ui.form.base.FormFieldsState
import com.keygenqt.kchat.modules.common.ui.form.fields.FieldEmail
import com.keygenqt.kchat.modules.common.ui.form.fields.FieldPassword
import com.keygenqt.kchat.modules.guest.ui.events.SignUpEvents
import com.keygenqt.kchat.modules.guest.ui.form.states.FormStatesSignUp.Email
import com.keygenqt.kchat.modules.guest.ui.form.states.FormStatesSignUp.Password
import com.keygenqt.kchat.modules.guest.ui.viewModels.GuestViewModel
import com.keygenqt.kchat.theme.KChatTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun SignUpScreen(
    viewModel: GuestViewModel,
    onNavigationEvent: (SignUpEvents) -> Unit = {},
) {
    val commonError: String? by viewModel.commonError.collectAsState(null)
    val loading: Boolean by viewModel.loading.collectAsState()

    SignUpBody(
        loading = loading,
        commonError = commonError,
        onNavigationEvent = onNavigationEvent,
    )
}

@Composable
fun SignUpBody(
    loading: Boolean = false,
    commonError: String? = null,
    onNavigationEvent: (SignUpEvents) -> Unit = {},
) {
    val listState = rememberScrollState()

    MainScaffold(
        isLoaderShow = loading,
        title = stringResource(id = R.string.sign_up_title),
        navigationIconOnClick = { onNavigationEvent(SignUpEvents.NavigateBack) }
    ) { innerPadding ->

        val modifier = Modifier.padding(innerPadding)
        val padding = 16.dp

        Column(
            modifier = modifier
                .padding(start = padding, end = padding)
                .background(MaterialTheme.colors.background)
                .verticalScroll(listState)
        ) {

            Spacer(modifier = Modifier.size(16.dp))

            // common error
            commonError?.let {
                FormError(textError = it)
                Spacer(Modifier.size(padding))
                LaunchedEffect(commonError) { listState.animateScrollTo(0) }
            }

            SignUpForm(
                loading = loading,
                onNavigationEvent = onNavigationEvent
            )

            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpForm(
    loading: Boolean = false,
    onNavigationEvent: (SignUpEvents) -> Unit = {},
) {
    val softwareKeyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    val padding = 16.dp

    val formFields = FormFieldsState().apply {
        add(Email, remember { Email.state.default("") })
        add(Password, remember { Password.state.default("") })
    }

    val requesterFieldEmail = remember { FocusRequester() }
    val requesterFieldPassword = remember { FocusRequester() }

    // click submit
    val submitClick = {
        // validate before send
        formFields.validate()
        // check has errors
        if (!formFields.hasErrors()) {
            // submit query
            onNavigationEvent(
                SignUpEvents.SignUp(
                    email = formFields.get(Email).getValue(),
                    password = formFields.get(Password).getValue(),
                )
            )
            // hide keyboard
            softwareKeyboardController?.hide()
        }
    }

    FieldEmail(
        modifier = Modifier.focusRequester(requesterFieldEmail),
        labelText = stringResource(id = R.string.form_email),
        enabled = !loading,
        state = formFields.get(Email),
        imeAction = ImeAction.Next,
        keyboardActions = KeyboardActions(onNext = { requesterFieldPassword.requestFocus() })
    )

    Spacer(modifier = Modifier.size(padding))

    FieldPassword(
        modifier = Modifier.focusRequester(requesterFieldPassword),
        enabled = !loading,
        state = formFields.get(Password),
        imeAction = ImeAction.Done,
        keyboardActions = KeyboardActions(onDone = { submitClick.invoke() })
    )

    Spacer(modifier = Modifier.size(padding))

    Button(
        enabled = !loading,
        onClick = { submitClick.invoke() },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.form_button_submit).uppercase(),
        )
    }

    LaunchedEffect(Unit) {
        scope.launch {
            delay(10)
            requesterFieldEmail.requestFocus()
        }
    }
}

@Preview("Light")
@Preview("Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewSignUpBody() {
    KChatTheme {
        Surface {
            SignUpBody()
        }
    }
}