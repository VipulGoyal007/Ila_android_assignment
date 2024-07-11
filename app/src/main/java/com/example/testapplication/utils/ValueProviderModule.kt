package com.example.testapplication.utils


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ValueProviderModule {
    @Singleton
    @Provides
    @Named(NamedConstants.CONTENT_TYPE)
    fun getContentType(): String = "application/json"


    @Singleton
    @Provides
    @Named(NamedConstants.ACCESS_TOKEN)
    fun getAccessToken(): String = "Bearer eyJhbGciOiJIUzUxMiIsInppcCI6IkRFRiJ9.eNqqViouTVKyUjK3NDexNDA3M7BU0lEqzi8tSk4Fiob4xjv6uQT5e7rEh5nrGekZACWL8nNSfTKLS5SsopWC_H1c451Dg0P8fV2DlGJ1lFIrCpSsDM1NjQxNjUzMzHWUMhNLQAJGBmaGZkCBWgAAAAD__w.M58pryeOJ0MZ_tbBxKMo1AJzyxeHt9TVWMYmmLvm6irA8pZLFk285pnjDAk5E2k97n5fu6FcWvr3j-8Q-KwV7A"


// fun getAccessToken(): String = SharedPrefManager.getInstance().loggedInUserAccessToken

}