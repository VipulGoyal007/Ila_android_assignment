package com.example.testapplication.utils

import com.example.testapplication.repository.MainDataRepository
import com.example.testapplication.repository.MainDataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindsMainDataRepository(mainDataRepositoryImpl: MainDataRepositoryImpl): MainDataRepository

    }