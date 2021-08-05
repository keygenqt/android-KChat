package com.keygenqt.kchat.modules.common.ui.compose

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.keygenqt.kchat.R
import com.keygenqt.kchat.theme.KChatTheme

@Composable
fun MainScaffold(
    label: String = "Label",
    isLoaderShow: Boolean = false,
    icon: ImageVector? = Icons.Filled.ArrowBack,
    navigationIconOnClick: () -> Unit = {},
    elevation: Dp = AppBarDefaults.TopAppBarElevation,
    contentDescription: String = stringResource(R.string.common_navigate_up),
    actions: @Composable ((RowScope) -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.primary,
                elevation = elevation,
                title = {
                    Text(
                        fontSize = TextUnit.Unspecified,
                        text = label,
                        color = Color.White
                    )
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
                    actions?.invoke(this)
                    if (isLoaderShow) {
                        Loader()
                    }
                }
            )
        },
        content = content,
    )
}

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