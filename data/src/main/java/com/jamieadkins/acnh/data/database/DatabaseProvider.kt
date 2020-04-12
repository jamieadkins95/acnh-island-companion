package com.jamieadkins.acnh.data.database

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    private var instance: AcnhDatabase? = null

    fun getInstance(context: Context): AcnhDatabase {
        val localInstance = instance ?: Room.databaseBuilder(context, AcnhDatabase::class.java, "acnh").fallbackToDestructiveMigration().build()
        instance = localInstance
        return localInstance
    }
}