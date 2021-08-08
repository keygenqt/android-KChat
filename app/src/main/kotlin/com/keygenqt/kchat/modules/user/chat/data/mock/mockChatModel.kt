package com.keygenqt.kchat.modules.user.chat.data.mock

import com.keygenqt.kchat.data.models.ChatModel

fun mockChatModel() = ChatModel(
    id = 1,
    userId = "admin",
    name = "Chat name",
    dateUpdated = System.currentTimeMillis(),
)