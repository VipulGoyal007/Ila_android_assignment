package com.example.testapplication.localdb

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class RoomDbModule {

    @Provides
    fun provideAppDao(@ApplicationContext context: Context): AppDataDao {
        return Room.databaseBuilder(context, AppRoomDatabase::class.java, "tm_database")
//            .addMigrations(AppRoomDatabase.MIGRATION_1_2)
            .fallbackToDestructiveMigration()
            .build()
            .getAppDao()
    }
}