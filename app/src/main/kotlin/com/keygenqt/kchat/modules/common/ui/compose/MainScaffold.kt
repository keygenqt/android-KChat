package com.keygenqt.kchat.modules.common.ui.compose

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.keygenqt.kchat.R
import com.keygenqt.kchat.modules.common.ui.form.states.StateSimpleEditText
import com.keygenqt.kchat.theme.KChatTheme

@ExperimentalComposeUiApi
@Composable
fun MainScaffold(
    label: String = "Label",
    isLoaderShow: Boolean = false,
    icon: ImageVector? = Icons.Filled.ArrowBack,
    navigationIconOnClick: () -> Unit = {},
    elevation: Dp = AppBarDefaults.TopAppBarElevation,
    contentDescription: String = stringResource(R.string.common_navigate_up),
    floatingActionButtonOnClick: () -> Unit = {},
    searchListener: (String?) -> Unit = {},
    contentFloatingActionButton: @Composable (() -> Unit)? = null,
    actions: @Composable ((RowScope) -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit,
) {

    val softwareKeyboardController = LocalSoftwareKeyboardController.current
    var isShowSearch by remember { mutableStateOf(false) }
    val state = remember { StateSimpleEditText() }
    val requester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.primary,
                elevation = elevation,
                title = {
                    Box {
                        if (isShowSearch) {
                            if (state.getValue().isEmpty()) {
                                Text(
                                    fontSize = TextUnit.Unspecified,
                                    text = stringResource(id = R.string.common_search),
                                    color = Color.White
                                )
                            }
                            BasicTextField(
                                singleLine = true,
                                value = state.text,
                                onValueChange = { state.text = it },
                                modifier = Modifier
                                    .focusRequester(requester)
                                    .fillMaxWidth()
                                    .onFocusChanged { focusState ->
                                        if (focusState.isFocused) {
                                            state.positionToEnd()
                                        }
                                    },
                                textStyle = MaterialTheme.typography.h6.merge(TextStyle(color = Color.White)),
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    capitalization = KeyboardCapitalization.Sentences,
                                    imeAction = ImeAction.Search
                                ),
                                keyboardActions = KeyboardActions(onSearch = {
                                    focusManager.clearFocus()
                                    searchListener(state.getValue())
                                    softwareKeyboardController?.hide()
                                }),
                                cursorBrush = SolidColor(Color.White)
                            )
                            LaunchedEffect(isShowSearch) {
                                requester.requestFocus()
                            }
                        } else {
                            Text(
                                fontSize = TextUnit.Unspecified,
                                text = label,
                                color = Color.White
                            )
                        }
                    }
                },
                navigationIcon = icon?.let {
                    {
                        IconButton(onClick = navigationIconOnClick) {
                            Icon(
                                imageVector = icon,
                                contentDescription = contentDescription,
                                tint = Color.White
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = {
                        state.clear()
                        isShowSearch = !isShowSearch
                        if (!isShowSearch) {
                            searchListener(null)
                            softwareKeyboardController?.hide()
                            requester.freeFocus()
                        }
                    }) {
                        if (isShowSearch) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Search",
                                tint = Color.White
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = Color.White
                            )
                        }
                    }
                    actions?.invoke(this)
                    if (isLoaderShow) {
                        Loader()
                    }
                }
            )
        },
        content = {
            Box {
                content.invoke(it)
                contentFloatingActionButton?.let {
                    FloatingActionButton(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.BottomEnd),
                        onClick = floatingActionButtonOnClick
                    ) {
                        contentFloatingActionButton.invoke()
                    }
                }
            }
        },
    )
}

@ExperimentalComposeUiApi
@Preview("Light")
@Preview("Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewMainScaffold() {
    KChatTheme {
        Surface {
            MainScaffold {}
        }
    }
}