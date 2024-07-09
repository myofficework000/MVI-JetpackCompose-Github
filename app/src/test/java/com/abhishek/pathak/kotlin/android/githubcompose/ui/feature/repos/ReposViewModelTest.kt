package com.abhishek.pathak.kotlin.android.githubcompose.ui.feature.repos

import com.abhishek.pathak.kotlin.android.githubcompose.data.GithubRepository
import com.abhishek.pathak.kotlin.android.githubcompose.data.model.Repo
import com.abhishek.pathak.kotlin.android.githubcompose.data.model.User
import com.abhishek.pathak.kotlin.android.githubcompose.data.model.UserDetail
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

@OptIn(ExperimentalCoroutinesApi::class)
class ReposViewModelTest{
    private val githubRepository = mockk<GithubRepository>()

    @Before
    fun setUp(){
        Dispatchers.setMain(Dispatchers.Unconfined)
    }
    @Test
    fun `when view model initialized then should emit initial view state first`() = runTest {
        val expectedInitialViewState = ReposContract.State(
            user = null,
            reposList = emptyList(),
            isUserLoading = true,
            isReposLoading = true,
            isError = false
        )
        val viewmodel = ReposViewModel("testUserId",githubRepository)
        assertEquals(expectedInitialViewState,viewmodel.viewState.value)
    }

    @Test
    fun `when fetchInitialData called then should emit user and repos state`() = runTest {
        val userId = "testUserId"
        val user = UserDetail(avatarUrl = "avatar_url", htmlUrl = "html_url")
        val repos = listOf(Repo(id = 1, name = "Repo 1", description = "Description 1"))
        val expectedViewState = ReposContract.State(
            user = user,
            reposList = repos,
            isUserLoading = false,
            isReposLoading = false,
            isError = false
        )
        coEvery { githubRepository.getUser(userId) } returns Result.success(user)
        coEvery { githubRepository.getRepos(userId) } returns Result.success(repos)

        val viewmodel = ReposViewModel(userId,githubRepository)

        assertEquals(expectedViewState,viewmodel.viewState.value)
    }

    @Test
    fun `test state after repos retrieval failure`() = runTest {
        val userId = "testUserId"
        val user = UserDetail(avatarUrl = "avatar_url", htmlUrl = "html_url")
        val expectedViewState = ReposContract.State(
            user = user,
            reposList = emptyList(),
            isUserLoading = false,
            isReposLoading = false,
            isError = true
        )

        coEvery { githubRepository.getUser(userId) } returns Result.success(user)
        coEvery { githubRepository.getRepos(userId) } returns Result.failure(Exception("Repos retrieval failed"))

        val viewmodel = ReposViewModel(userId,githubRepository)

        assertEquals(expectedViewState,viewmodel.viewState.value)
    }
}