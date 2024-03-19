package com.example.trackmysleep.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "sleepNights")
data class SleepNight(
    @PrimaryKey(autoGenerate = true)
    var nightId: Long = 0L,

    @ColumnInfo(name = "startTimeMili")
    var startTimeMili: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "endTime")

    var endTime: Long = startTimeMili,
    @ColumnInfo(name = "sleepQuality")

    var sleepQuality: Int = -1

)
