package com.example.testapplication.localdb

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testapplication.datamodel.SearchDataModel

@Dao
interface TruemedsDao {

    //=======================Insert Queries=======================
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOriginalMedicine(medicine: CartMedicine)

    @Query(
        "select * " +
                "from addedMedsTable12 " )
    fun getMedsList(): List<CartMedicine>
}

