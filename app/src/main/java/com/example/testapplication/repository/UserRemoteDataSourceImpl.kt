package com.example.testapplication.repository


import com.example.testapplication.datamodel.GetAllPatientResponse
import com.example.testapplication.utils.NamedConstants
import com.example.testapplication.utils.UserDataApi
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named

class UserRemoteDataSourceImpl @Inject constructor(
    private val userDataApi: UserDataApi,
    @Named(NamedConstants.CONTENT_TYPE) private val contentType: String,
    @Named(NamedConstants.ACCESS_TOKEN) private val access_token: String,

) : UserRemoteDataSource {


    override suspend fun getAllPatient(
        showMyself: Boolean?,
        customerId: Long?
    ): Response<GetAllPatientResponse> {
        return userDataApi.getAllPatient(contentType,access_token, showMyself, customerId)
    }

}