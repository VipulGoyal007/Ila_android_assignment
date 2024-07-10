package com.example.testapplication.utils

import com.example.testapplication.BuildConfig


object ApiEndpointConstants {
    fun baseUrl(): String {
        return BuildConfig.baseUrl
    }
}