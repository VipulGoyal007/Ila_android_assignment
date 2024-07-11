package com.example.testapplication.localdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "addedMedsTable12")
class CartMedicine(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long?,
    @ColumnInfo(name = "medicineId") val medicineId: String,
    @ColumnInfo(name = "medicineName") val medicineName: String?,
    )
    
