package com.abhishek.pathak.kotlin.android.githubcompose.di

import com.abhishek.pathak.kotlin.android.githubcompose.data.GithubRepository
import com.abhishek.pathak.kotlin.android.githubcompose.data.GithubRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    factory<GithubRepository> {
        GithubRepositoryImpl(
            githubApi = get()
        )
    }
}