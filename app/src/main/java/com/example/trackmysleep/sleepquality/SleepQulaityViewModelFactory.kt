package com.example.trackmysleep.sleepquality

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trackmysleep.database.SleepNightDao

class SleepQulaityViewModelFactory(
    val id: Long,
    val databaseDao: SleepNightDao,
) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(SleepQualityViewModel::class.java)) {
            return SleepQualityViewModel(id, databaseDao) as T
        }

        throw IllegalArgumentException("ERROR")
    }
}