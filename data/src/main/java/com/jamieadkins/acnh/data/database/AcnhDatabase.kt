package com.jamieadkins.acnh.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CaughtCritter::class], version = 1)
abstract class AcnhDatabase : RoomDatabase() {

    abstract fun caughtDao(): CaughtCritterDao
}