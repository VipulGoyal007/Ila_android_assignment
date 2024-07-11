package com.example.testapplication.localdb

import android.util.Log
import com.example.testapplication.datamodel.SearchDataModel
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class LocalDbRepositoryImpl @Inject constructor(private val appDao: TruemedsDao) :
    LocalDbRepository {
    override suspend fun addItemToCart(medicine: CartMedicine) {
        appDao.insertOriginalMedicine(medicine)
    }

    override suspend fun getAddedMedicines(): List<CartMedicine> {
        return appDao.getMedsList()
    }
}