package com.example.testapplication.localdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AppDataDao {

    //=======================Insert Queries=======================
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOriginalMedicine(medicine: CartMedicine)

    @Query(
        "select * " +
                "from addedMedsTable12 " )
    fun getMedsList(): List<CartMedicine>
}

