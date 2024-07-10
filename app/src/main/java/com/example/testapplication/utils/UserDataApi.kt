package com.example.testapplication.utils

import com.example.testapplication.datamodel.GetAllPatientResponse
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query


interface UserDataApi {

    @POST( "/CustomerService/getAllPatient")
    suspend fun getAllPatient(
        @Header("Content-Type") contentType: String?,
        @Header("Authorization") authorization: String?,
        @Query("showMyself") showMyself: Boolean?,
        @Query("customerId") customerId: Long?
    ): Response<GetAllPatientResponse>


}