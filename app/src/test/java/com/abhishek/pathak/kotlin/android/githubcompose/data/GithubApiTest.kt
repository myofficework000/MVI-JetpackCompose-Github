package com.abhishek.pathak.kotlin.android.githubcompose.data

import com.abhishek.pathak.kotlin.android.githubcompose.data.model.Repo
import com.abhishek.pathak.kotlin.android.githubcompose.data.model.User
import com.abhishek.pathak.kotlin.android.githubcompose.data.model.UserDetail
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GithubApiTest {

    private val mockWebService = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val api = Retrofit.Builder()
        .baseUrl(mockWebService.url("/"))
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(GithubApi::class.java)

    @After
    fun tearDown() {
        mockWebService.shutdown()
    }

    @Test
    fun `Given 200 response When fetching users Then returns users correctly`() {
        // Given
        mockWebService.enqueueResponse(
            fileName = "users.json",
            code = 200
        )
        val expected = listOf(
            User(
                userId = "myofficework000",
                avatarUrl = "https://avatars.githubusercontent.com/u/51234843?v=4",
                htmlUrl = "https://github.com/myofficework000",
            ),
            User(
                userId = "AGLPHO3NIX",
                avatarUrl = "https://avatars.githubusercontent.com/u/51234844?v=4",
                htmlUrl = "https://github.com/AGLPHO3NIX",
            ),
            User(
                userId = "gulamyakkaspace",
                avatarUrl = "https://avatars.githubusercontent.com/u/51234845?v=4",
                htmlUrl = "https://github.com/gulamyakkaspace",
            ),
        )

        // When
        val actual = runBlocking { api.getUsers() }
        val request = mockWebService.takeRequest()

        // Then
        assertEquals(expected, actual)
        assertEquals("/users?since=51234842", request.path)
    }

    @Test
    fun `Given 404 response when fetching users the throws HTTPException`(){
        mockWebService.enqueueResponse(
            fileName = "not-found.json",
            code = 404
        )
        val exception = assertFailsWith<HttpException> {
            runBlocking { api.getUsers() }
        }
        assertEquals(404,exception.code())
    }

    @Test
    fun `Given 200 response When fetching user Then returns user correctly`() {
        // Given
        val userId = "mojombo"
        mockWebService.enqueueResponse(
            fileName = "user-detail.json",
            code = 200
        )
        val expected = UserDetail(
            avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
            htmlUrl = "https://github.com/mojombo",
            name = "Tom Preston-Werner",
            location = "San Francisco",
            blogUrl = "http://tom.preston-werner.com",
            publicRepos = 64,
            followers = 22991,
            following = 11,
        )

        // When
        val actual = runBlocking { api.getUser(userId) }
        val request = mockWebService.takeRequest()

        // Then
        assertEquals(expected, actual)
        assertEquals("/users/$userId", request.path)
    }

    @Test
    fun `Given 200 response When fetching repos Then returns repos correctly`() {
        // Given
        val userId = "mojombo"
        mockWebService.enqueueResponse(
            fileName = "repos.json",
            code = 200
        )

        val expected = listOf(
            Repo(
                id = 26899533,
                name = "30daysoflaptops.github.io",
                description = null,
                watchersCount = 7,
                forksCount = 2,
                stargazersCount = 7,
                language = "CSS",
                htmlUrl = "https://github.com/mojombo/30daysoflaptops.github.io",
            ),
            Repo(
                id = 17358646,
                name = "asteroids",
                description = "Destroy your Atom editor, Asteroids style!",
                watchersCount = 93,
                forksCount = 13,
                stargazersCount = 93,
                language = "JavaScript",
                htmlUrl = "https://github.com/mojombo/asteroids",
            ),
            Repo(
                id = 29941343,
                name = "benbalter.github.com",
                description = "The personal website of Ben Balter. Built using Jekyll and GitHub Pages. See humans.txt for more infos.",
                watchersCount = 5,
                forksCount = 7,
                stargazersCount = 5,
                language = "CSS",
                htmlUrl = "https://github.com/mojombo/benbalter.github.com",
            ),
        )

        // When
        val actual = runBlocking { api.getRepos(userId) }
        val request = mockWebService.takeRequest()

        // Then
        assertEquals(expected, actual)
        assertEquals("/users/$userId/repos", request.path)
    }

    @Test
    fun `Given 404 When fetching user Then throws HttpException`(){
        val userId = "nonexistentuser"
        mockWebService.enqueueResponse(
            fileName = "not-found.json",
            code = 404
        )
        val exception = assertFailsWith<HttpException> {
            runBlocking { api.getUser(userId) }
        }
        assertEquals(404,exception.code())
        assertEquals("/users/$userId", mockWebService.takeRequest().path)
    }
}
