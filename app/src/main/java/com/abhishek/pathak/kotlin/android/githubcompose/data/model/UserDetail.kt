package com.abhishek.pathak.kotlin.android.githubcompose.data.model

import com.google.gson.annotations.SerializedName

data class UserDetail(
    @SerializedName("avatar_url") val avatarUrl: String = "",
    @SerializedName("html_url") val htmlUrl: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("location") val location: String? = "",
    @SerializedName("blog") val blogUrl: String = "",
    @SerializedName("public_repos") val publicRepos: Int = 0,
    @SerializedName("followers") val followers: Int = 0,
    @SerializedName("following") val following: Int = 0,
)

fun buildUserDetailPreview() = UserDetail(
    avatarUrl = "https://avatars.githubusercontent.com/myofficework000",
    htmlUrl = "https://github.com/51234843",
    name = "Abhishek Pathak",
    location = "London",
    publicRepos = 50,
    followers = 50,
    following = 0
)
