package com.example.testapplication.repository


import com.example.testapplication.datamodel.GetAllPatientResponse
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Response

interface UserRemoteDataSource {

    suspend fun getAllPatient(
        showMyself: Boolean?,
        customerId: Long?
    ): Response<GetAllPatientResponse>

}