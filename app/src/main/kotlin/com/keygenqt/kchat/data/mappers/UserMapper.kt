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
 
package com.keygenqt.kchat.data.mappers

import com.keygenqt.kchat.data.models.UserModel
import com.keygenqt.kchat.data.responses.UserResponses

fun UserResponses.toModel(): UserModel {
    return UserModel(
        id = id,
        login = login,
        avatarUrl = avatar_url,
        followersUrl = followers_url,
        reposUrl = repos_url,
        name = name,
        bio = bio ?: "",
        createdAt = created_at,
    )
}

fun List<UserResponses>.toModels(): List<UserModel> {
    return map { it.toModel() }
}