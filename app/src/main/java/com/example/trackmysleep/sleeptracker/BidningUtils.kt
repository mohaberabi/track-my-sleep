package com.example.trackmysleep.sleeptracker

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.trackmysleep.R
import com.example.trackmysleep.convertLongToDateString
import com.example.trackmysleep.convertNumericQualityToString
import com.example.trackmysleep.database.SleepNight


@BindingAdapter("sleepDurationFormatted")
fun TextView.setSleepDurationFormatted(item: SleepNight?) {


    item?.let {
        text = convertLongToDateString(item.startTimeMili)
    }


}

@BindingAdapter("sleepQualityString")

fun TextView.setSleepQualityString(item: SleepNight?) {
    item?.let {
        text = convertNumericQualityToString(item.sleepQuality, context.resources)
    }
}

@BindingAdapter("sleepImage")


fun ImageView.setSleepImage(item: SleepNight?) {
    item?.let {
        setImageResource(
            when (item.sleepQuality) {
                0 -> R.drawable.ic_sleep_0
                1 -> R.drawable.ic_sleep_1
                2 -> R.drawable.ic_sleep_2
                3 -> R.drawable.ic_sleep_3
                4 -> R.drawable.ic_sleep_4
                else -> R.drawable.ic_sleep_5

            }
        )
    }
}