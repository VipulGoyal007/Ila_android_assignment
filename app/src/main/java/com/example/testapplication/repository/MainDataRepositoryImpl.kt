package com.example.testapplication.repository

import com.example.testapplication.datamodel.GetAllPatientResponse
import com.example.testapplication.utils.ApiCallInitClass
import com.example.testapplication.utils.Resource
import com.intellihealth.truemeds.data.model.mixpanel.MxInternalErrorOccurred
import retrofit2.Response
import javax.inject.Inject

class MainDataRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val apiCallInitClass: ApiCallInitClass
) : MainDataRepository {

    override suspend fun getAllPatient(
        mxInternalErrorOccurred: MxInternalErrorOccurred,
        showMyself: Boolean?,
        customerId: Long?
    ): Resource<Response<GetAllPatientResponse>> {
         return apiCallInitClass.safeApiCall(mxInternalErrorOccurred) {userRemoteDataSource.getAllPatient(showMyself,customerId)}
    }
}