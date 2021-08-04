package com.keygenqt.android_kchat.extensions

import io.ktor.client.features.websocket.*
import io.ktor.http.cio.websocket.*
import timber.log.Timber

suspend fun DefaultClientWebSocketSession.outputMessages() {
    try {
        for (message in incoming) {
            message as? Frame.Text ?: continue
            val receivedText = message.readText()
            Timber.e("receivedText: $receivedText")
        }
    } catch (e: Exception) {
        Timber.e("Error while receiving: %s", e.localizedMessage)
    }
}

suspend fun DefaultClientWebSocketSession.inputMessages(message: String) {
    try {
        send(message)
    } catch (e: Exception) {
        Timber.e("Error while sending: %s", e.localizedMessage)
        return
    }
}