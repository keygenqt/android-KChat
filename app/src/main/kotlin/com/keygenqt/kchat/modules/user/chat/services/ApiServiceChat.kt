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
 
package com.keygenqt.kchat.modules.user.chat.services

import com.keygenqt.kchat.BuildConfig
import com.keygenqt.kchat.base.ResponseResult
import com.keygenqt.kchat.base.executeWithResponse
import com.keygenqt.kchat.data.mappers.toModel
import com.keygenqt.kchat.data.mappers.toModels
import com.keygenqt.kchat.data.models.ChatModel
import com.keygenqt.kchat.data.requests.ChatCreateRequest
import com.keygenqt.kchat.data.responses.ChatResponses
import com.keygenqt.kchat.utils.ConstantsApp
import com.keygenqt.kchat.utils.ConstantsPaging.PAGE_LIMIT
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApiServiceChat @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun getListChats(offset: Int, search: String? = null): ResponseResult<List<ChatModel>> {
        return withContext(Dispatchers.IO) {
            if (BuildConfig.DEBUG) delay(1000L) // Simulate slow internet
            executeWithResponse {
                httpClient.get<List<ChatResponses>>("${ConstantsApp.API_URL}/chats") {
                    search?.let { parameter("search", search) }
                    parameter("offset", offset)
                    parameter("limit", PAGE_LIMIT)
                }.toModels()
            }
        }
    }

    suspend fun createChat(name: String): ResponseResult<ChatModel> {
        return withContext(Dispatchers.IO) {
            executeWithResponse {
                httpClient.post<ChatResponses>("${ConstantsApp.API_URL}/chats") {
                    contentType(ContentType.Application.Json)
                    body = ChatCreateRequest(userId = "client", name = name)
                }.toModel()
            }
        }
    }
}