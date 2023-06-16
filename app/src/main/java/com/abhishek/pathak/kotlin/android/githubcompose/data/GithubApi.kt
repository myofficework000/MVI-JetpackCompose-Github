package com.abhishek.pathak.kotlin.android.githubcompose.data

import com.abhishek.pathak.kotlin.android.githubcompose.data.model.Repo
import com.abhishek.pathak.kotlin.android.githubcompose.data.model.User
import com.abhishek.pathak.kotlin.android.githubcompose.data.model.UserDetail
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {
    @GET(Endpoints.GET_USERS)
    suspend fun getUsers(): List<User>

    @GET(Endpoints.GET_USER)
    suspend fun getUser(@Path("userLogin") userId: String): UserDetail?

    @GET(Endpoints.GET_REPOS_BY_USER)
    suspend fun getRepos(@Path("userLogin") userId: String): List<Repo>
}
