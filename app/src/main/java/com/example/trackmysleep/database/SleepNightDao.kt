package com.example.trackmysleep.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SleepNightDao {


    @Insert
    fun insert(sleepNight: SleepNight)

    @Update
    fun update(sleepNight: SleepNight)

    @Query("SELECT * from sleepNights WHERE nightId = :id")
    fun getSleepNight(id: Long): SleepNight


    @Delete
    fun deleteAllNights(nights: List<SleepNight>): Int

    @Query("DELETE FROM sleepNights")
    fun clear()


    @Query("SELECT * FROM sleepNights ORDER BY nightId DESC")
    fun getAllNights(): LiveData<List<SleepNight>>


    @Query("SELECT * FROM sleepNights ORDER BY nightId DESC LIMIT 1")
    fun getTonight(): SleepNight?
}