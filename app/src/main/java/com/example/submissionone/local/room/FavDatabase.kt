package com.example.submissionone.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.submissionone.local.entity.FavEntity
import com.example.submissionone.local.room.FavDao

@Database(entities = [FavEntity::class], version = 1)
abstract class FavDatabase : RoomDatabase() {
    abstract fun favDao(): FavDao

    companion object {
        @Volatile
        private var instance: FavDatabase? = null
        fun getInstance(context: Context): FavDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    FavDatabase::class.java, "fav_db"
                ).build()
            }
    }
}