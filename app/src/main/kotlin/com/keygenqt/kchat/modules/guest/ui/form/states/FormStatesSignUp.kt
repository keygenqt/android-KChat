package com.keygenqt.kchat.modules.guest.ui.form.states

import com.keygenqt.kchat.modules.common.ui.form.base.FormFieldState
import com.keygenqt.kchat.modules.common.ui.form.base.FormStates
import com.keygenqt.kchat.modules.common.ui.form.states.EmailStateRequired
import com.keygenqt.kchat.modules.common.ui.form.states.PasswordState

enum class FormStatesSignUp(val state: FormFieldState) : FormStates {
    Email(EmailStateRequired()),
    Password(PasswordState()),
}