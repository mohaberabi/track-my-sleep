package com.example.trackmysleep.sleepquality

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackmysleep.database.SleepNightDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SleepQualityViewModel(
    private val sleepNightId: Long,
    private val databaseDao: SleepNightDao,
) : ViewModel() {


    private val _navigateToTracker = MutableLiveData<Boolean>(false)

    val navigateToTracker: LiveData<Boolean>
        get() = _navigateToTracker


    fun doneNaivgating() {
        _navigateToTracker.value = false
    }

    fun onSetQuality(quality: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val tonight = databaseDao.getSleepNight(sleepNightId)
            tonight.sleepQuality = quality
            databaseDao.update(tonight)
        }
        _navigateToTracker.value = true
    }
}