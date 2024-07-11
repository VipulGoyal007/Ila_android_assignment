package com.example.testapplication.localdb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CartMedicine::class], version = 5, exportSchema = false
)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun getAppDao(): AppDataDao

}
