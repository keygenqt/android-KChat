package com.keygenqt.kchat.modules.common.ui.form.fields

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.keygenqt.kchat.R
import com.keygenqt.kchat.modules.common.ui.compose.TextFieldError
import com.keygenqt.kchat.modules.common.ui.form.base.FormFieldState
import com.keygenqt.kchat.modules.common.ui.form.states.EmailStateRequired
import com.keygenqt.kchat.theme.KChatTheme

@ExperimentalComposeUiApi
@Composable
fun FieldEmail(
    modifier: Modifier = Modifier,
    labelText: String = stringResource(id = R.string.form_email),
    enabled: Boolean = true,
    state: FormFieldState = remember { EmailStateRequired() },
    imeAction: ImeAction = ImeAction.Next,
    keyboardActions: KeyboardActions = KeyboardActions(),
) {
    TextField(
        maxLines = 1,
        singleLine = true,
        enabled = enabled,
        value = state.text,
        onValueChange = { state.text = it },
        label = { Text(labelText) },
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    state.positionToEnd()
                }
            },
        textStyle = MaterialTheme.typography.body2,
        isError = state.hasErrors,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = imeAction),
        keyboardActions = keyboardActions
    )

    state.getError(LocalContext.current)?.let { error ->
        TextFieldError(textError = error)
    }
}

@ExperimentalComposeUiApi
@Preview("Light")
@Preview("Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewFieldEmail() {
    KChatTheme {
        Surface {
            FieldEmail()
        }
    }
}