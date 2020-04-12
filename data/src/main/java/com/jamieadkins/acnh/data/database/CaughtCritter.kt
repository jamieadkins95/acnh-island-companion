package com.jamieadkins.acnh.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "caught")
data class CaughtCritter(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "caught") val caught: Boolean
)