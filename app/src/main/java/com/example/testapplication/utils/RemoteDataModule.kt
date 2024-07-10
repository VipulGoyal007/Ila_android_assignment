package com.example.testapplication.utils

import com.example.testapplication.repository.UserRemoteDataSource
import com.example.testapplication.repository.UserRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataModule {

    @Binds
    abstract fun bindsUserDataRemoteDataSource(userDataRemoteDataSourceImpl: UserRemoteDataSourceImpl): UserRemoteDataSource
}