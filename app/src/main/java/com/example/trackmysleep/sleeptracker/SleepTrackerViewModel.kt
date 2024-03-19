package com.example.trackmysleep.sleeptracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.trackmysleep.database.SleepNight
import com.example.trackmysleep.database.SleepNightDao
import com.example.trackmysleep.formatNights
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SleepTrackerViewModel(
    val dataBaseDao: SleepNightDao,
    val app: Application
) : AndroidViewModel(app) {


    private val _navigateToSleepQuality = MutableLiveData<SleepNight?>()
    val navigateToSleepQuality: LiveData<SleepNight?>
        get() = _navigateToSleepQuality

    fun doneNavigation() {
        _navigateToSleepQuality.value = null
    }

    private val _tonight = MutableLiveData<SleepNight?>()
    val tonight: LiveData<SleepNight?>
        get() = _tonight

    val nights: LiveData<List<SleepNight>> = dataBaseDao.getAllNights()

    val nightsString = nights.map {
        formatNights(it, app.resources)
    }

    init {
        initTonight()
    }

    private fun initTonight() {
        viewModelScope.launch {
            _tonight.value = getTonightFromDatabase()
        }
    }

    fun onStartTracking() {
        viewModelScope.launch {
            val newNight = SleepNight()
            insert(newNight)
            _tonight.value = getTonightFromDatabase()
        }
    }

    private suspend fun insert(night: SleepNight) {
        withContext(Dispatchers.IO) {
            dataBaseDao.insert(night)
        }
    }

    fun onStopTracking() {
        viewModelScope.launch {
            val oldNight = _tonight.value ?: return@launch
            oldNight.endTime = System.currentTimeMillis()
            update(oldNight)
            _navigateToSleepQuality.value = oldNight
        }
    }

    private suspend fun getTonightFromDatabase(): SleepNight? {
        return withContext(Dispatchers.IO) {
            var night = dataBaseDao.getTonight()
            if (night?.endTime != night?.startTimeMili) {
                night = null
            }
            night
        }
    }

    private suspend fun update(night: SleepNight) {
        withContext(Dispatchers.IO) {
            dataBaseDao.update(night)
        }
    }


    private val _navigateToSleepQualityWithId = MutableLiveData<Long?>()
    val navigateToSleepQualityWithId: LiveData<Long?>
        get() = _navigateToSleepQualityWithId

    fun onSleepNightClick(id: Long) {
        _navigateToSleepQualityWithId.value = id
    }

    fun onDoneGettingIdWithNavigation() {
        _navigateToSleepQualityWithId.value = null
    }

    fun onClear() {
        viewModelScope.launch {
            clear()
            _tonight.value = null
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            dataBaseDao.clear()
        }
    }
}
