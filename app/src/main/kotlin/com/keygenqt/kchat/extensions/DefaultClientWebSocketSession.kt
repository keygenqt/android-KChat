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
 
package com.keygenqt.kchat.extensions

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