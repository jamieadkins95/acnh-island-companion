package com.jamieadkins.acnh.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Maybe
import io.reactivex.Observable

@Dao
interface CaughtCritterDao {

    @Query("SELECT * FROM caught")
    fun getAll(): Observable<List<CaughtCritter>>

    @Query("SELECT caught FROM caught WHERE id=:id ")
    fun isCaught(id: String): Maybe<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(caught: CaughtCritter)
}