package com.example.testapplication.localdb

import javax.inject.Inject

class LocalDbRepositoryImpl @Inject constructor(private val appDao: AppDataDao) :
    LocalDbRepository {
    override suspend fun addItemToCart(medicine: CartMedicine) {
        appDao.insertOriginalMedicine(medicine)
    }

    override suspend fun getAddedMedicines(): List<CartMedicine> {
        return appDao.getMedsList()
    }
}