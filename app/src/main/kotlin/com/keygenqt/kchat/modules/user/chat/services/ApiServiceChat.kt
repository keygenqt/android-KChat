package com.keygenqt.kchat.modules.user.chat.services

import com.keygenqt.kchat.base.ResponseResult
import com.keygenqt.kchat.base.executeWithResponse
import com.keygenqt.kchat.data.mappers.toModels
import com.keygenqt.kchat.data.models.ChatModel
import com.keygenqt.kchat.data.responses.ChatResponses
import com.keygenqt.kchat.utils.ConstantsApp
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApiServiceChat @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun getListChats(): ResponseResult<List<ChatModel>> {
        return withContext(Dispatchers.IO) {
            executeWithResponse {
                httpClient.get<List<ChatResponses>>("${ConstantsApp.API_URL}/chats").toModels()
            }
        }
    }
}