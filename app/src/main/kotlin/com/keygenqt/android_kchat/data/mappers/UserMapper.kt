package com.keygenqt.android_kchat.data.mappers

import com.keygenqt.android_kchat.data.models.UserModel
import com.keygenqt.android_kchat.data.responses.UserResponses

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