package com.keygenqt.kchat.modules.common.ui.form.validation

import android.content.Context
import com.keygenqt.kchat.R

fun getErrorIsBlank(target: String) =
    when {
        target.isBlank() -> { it: Context ->
            it.getString(R.string.is_required)
        }
        else -> null
    }
