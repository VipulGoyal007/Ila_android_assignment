package com.example.testapplication.repository


import com.example.testapplication.datamodel.GetAllPatientResponse
import com.example.testapplication.utils.Resource
import com.google.gson.JsonObject
import com.intellihealth.truemeds.data.model.mixpanel.MxInternalErrorOccurred
import okhttp3.ResponseBody
import retrofit2.Response

interface MainDataRepository {

    /**
     * This service provides all patient list of customer
     * **/
    suspend fun getAllPatient(
        mxInternalErrorOccurred: MxInternalErrorOccurred,
        showMyself: Boolean?, customerId: Long?
    ): Resource<Response<GetAllPatientResponse>>

}