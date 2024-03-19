package com.example.trackmysleep.sleeptracker

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trackmysleep.database.AppDatabase
import com.example.trackmysleep.database.SleepNightDao

class SleepTrackerViewModelFactory(
    private val application: Application,
    private val appDataBaseDao: SleepNightDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepTrackerViewModel::class.java)) {
            return SleepTrackerViewModel(
                dataBaseDao = appDataBaseDao,
                app = application
            ) as T
        }

        throw IllegalArgumentException("unkown uncheked viewmodel")
    }
}