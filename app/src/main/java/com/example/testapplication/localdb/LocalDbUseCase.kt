package com.example.testapplication.localdb

import android.util.Log
import com.example.testapplication.datamodel.SearchDataModel

import javax.inject.Inject

class LocalDbUseCase @Inject constructor(
    private val localDbRepository: LocalDbRepository
) {

    /**
     * Add product in offline DB
     */
    suspend fun addToCart(item: CartMedicine) {
            localDbRepository.addItemToCart(item)

    }


    suspend fun getAddedMedicines(): List<CartMedicine> {
        return localDbRepository.getAddedMedicines() ?: listOf()
    }
}