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
 
package com.keygenqt.kchat.modules.user.chat.ui.compose.listChats

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
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
import com.keygenqt.kchat.modules.common.ui.form.base.FormFieldsState
import com.keygenqt.kchat.modules.common.ui.form.fields.FieldSimpleEditText
import com.keygenqt.kchat.modules.user.chat.ui.events.ListChatsEvents
import com.keygenqt.kchat.modules.user.chat.ui.form.states.FormStatesCreateChat.ChatName
import com.keygenqt.kchat.theme.KChatTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ListChatsScreenAddDialog(
    createChatSuccess: Boolean? = null,
    onNavigationEvent: (ListChatsEvents) -> Unit = {},
    openDialog: MutableState<Boolean> = remember { mutableStateOf(false) },
    nameRequester: FocusRequester = remember { FocusRequester() },
) {
    if (openDialog.value) {

        val scope = rememberCoroutineScope()

        val formFields = FormFieldsState().apply {
            add(ChatName, remember { ChatName.state.default("") })
        }

        // click submit
        val submitClick = {
            // validate before send
            formFields.validate()
            // check has errors
            if (!formFields.hasErrors()) {
                onNavigationEvent(
                    ListChatsEvents.CreateChat(
                        formFields.get(ChatName).getValue()
                    )
                )
            }
        }

        if (createChatSuccess == false) {
            formFields.get(ChatName).setError { context: Context ->
                context.getString(R.string.is_exist)
            }
        }

        AlertDialog(
            modifier = Modifier
                .padding(16.dp),
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = stringResource(id = R.string.list_chats_create_chat_title))
            },
            text = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column {

                        FieldSimpleEditText(
                            modifier = Modifier.focusRequester(nameRequester),
                            labelText = stringResource(id = R.string.form_name),
                            state = formFields.get(ChatName),
                            imeAction = ImeAction.Done,
                            keyboardActions = KeyboardActions(onDone = { submitClick.invoke() })
                        )

                        val softwareKeyboardController = LocalSoftwareKeyboardController.current

                        DisposableEffect(Unit) {
                            // focus to name
                            nameRequester.requestFocus()
                            // load for open keyboard
                            scope.launch {
                                delay(300)
                                softwareKeyboardController?.show()
                            }
                            onDispose {
                                // clear error form
                                formFields.clearError()
                                // hide keyboard after close dialog
                                softwareKeyboardController?.hide()
                            }
                        }
                    }
                }

            },
            confirmButton = {
                Button(
                    onClick = { submitClick.invoke() }
                ) {
                    Text(text = stringResource(id = R.string.common_btn_confirm))
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { openDialog.value = false },
                ) {
                    Text(text = stringResource(id = R.string.common_btn_cancel))
                }
            }
        )
    }
}

@Preview("Light")
@Preview("Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewListChatsScreenAddDialog() {
    KChatTheme {
        Surface {
            ListChatsScreenAddDialog()
        }
    }
}

