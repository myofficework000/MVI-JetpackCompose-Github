package com.abhishek.pathak.kotlin.android.githubcompose.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("login") val userId: String = "",
    @SerializedName("avatar_url") val avatarUrl: String = "",
    @SerializedName("html_url") val htmlUrl: String = "",
)

fun buildUserPreview() = User(
    userId = "51234843",
    avatarUrl = "https://avatars.githubusercontent.com/myofficework000",
    htmlUrl = "https://github.com/51234843",
)
