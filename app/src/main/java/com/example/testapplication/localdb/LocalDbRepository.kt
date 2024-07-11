package com.example.testapplication.localdb

import com.example.testapplication.datamodel.SearchDataModel


interface LocalDbRepository {
    suspend fun addItemToCart(medicine: CartMedicine)
    suspend fun getAddedMedicines(): List<CartMedicine>

    }
