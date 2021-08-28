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
 
package com.keygenqt.kchat.modules.common.ui.form.states

import android.content.Context
import com.keygenqt.kchat.R
import com.keygenqt.kchat.modules.common.ui.form.base.FormFieldState
import com.keygenqt.kchat.modules.common.ui.form.validation.getErrorIsBlank
import java.util.regex.Pattern

private const val PASSWORD_VALIDATION_REGEX = """^[0-9_A-z]+$"""

class PasswordState : FormFieldState(checkValid = ::checkValid)

private fun checkValid(target: String) = listOfNotNull(
    getErrorIsBlank(target),
    getErrorIsSmall(target),
    getErrorIsLong(target),
    getErrorIsNotMatches(target),
)

private fun getErrorIsSmall(target: String) =
    when {
        target.length < 6 -> { it: Context ->
            it.getString(R.string.error_field_min_length, "6")
        }
        else -> null
    }

private fun getErrorIsLong(target: String) =
    when {
        target.length > 12 -> { it: Context ->
            it.getString(R.string.error_field_max_length, "12")
        }
        else -> null
    }

private fun getErrorIsNotMatches(target: String) =
    when {
        !Pattern.matches(PASSWORD_VALIDATION_REGEX, target) -> { it: Context ->
            it.getString(R.string.is_incorrect)
        }
        else -> null
    }
