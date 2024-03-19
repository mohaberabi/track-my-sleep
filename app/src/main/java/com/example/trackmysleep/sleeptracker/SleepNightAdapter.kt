package com.example.trackmysleep.sleeptracker

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.trackmysleep.R
import com.example.trackmysleep.database.SleepNight
import com.example.trackmysleep.databinding.ListItemSleepNightBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ClassCastException


private const val ITEM_VIEW_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

class SleepNightAdapter(
    private val clickListener: SleepNightListener
) :
    androidx.recyclerview.widget.ListAdapter<DataItem, ViewHolder>(
        SleepNightDiffCallBack()
    ) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)


    fun addHeader(list: List<SleepNight>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map {
                    DataItem.SleepNightItem(it)
                }

            }


            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    class SleepNightViewHolder private constructor(
        private val binding: ListItemSleepNightBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: SleepNight, clickListener: SleepNightListener) {
            binding.dayNight = item

            binding.clickListener = clickListener
            binding.executePendingBindings()

        }

//        fun bind(item: SleepNight) {
//            binding.qualityImage.setImageResource(
//                when (item.sleepQuality) {
//                    0 -> R.drawable.ic_sleep_0
//                    1 -> R.drawable.ic_sleep_1
//                    2 -> R.drawable.ic_sleep_2
//                    3 -> R.drawable.ic_sleep_3
//                    4 -> R.drawable.ic_sleep_4
//                    else -> R.drawable.ic_sleep_5
//                }
//            )
//
//            binding.qualityName.text = item.sleepQuality.toString()
//            binding.dayName.text = item.startTimeMili.toString()
//        }

        companion object {
            fun from(parent: ViewGroup): SleepNightViewHolder {

                val inflator = LayoutInflater.from(parent.context)
                val binding = ListItemSleepNightBinding.inflate(
                    inflator,
                    parent,
                    false
                )


                return SleepNightViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {

        return when (viewType) {
            ITEM_VIEW_HEADER -> SleepNightViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)

            else -> throw ClassCastException("unknown ${viewType}")
        }
    }

    override fun getItemViewType(position: Int): Int {

        return when (getItem(position)) {

            is DataItem.Header -> ITEM_VIEW_HEADER
            is DataItem.SleepNightItem -> ITEM_VIEW_TYPE_ITEM
        }

    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {


        when (holder) {
            is SleepNightViewHolder -> {

                val nightITem = getItem(position) as DataItem.SleepNightItem

                holder.bind(nightITem.item, clickListener)
            }
        }


    }


}

class SleepNightDiffCallBack : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(
        oldItem: DataItem,
        newItem: DataItem
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: DataItem,
        newItem: DataItem
    ): Boolean {
        return oldItem.id == newItem.id
    }

}

class SleepNightListener(
    val clickListener: (sleepId: Long) -> Unit
) {
    fun onClick(night: SleepNight) = clickListener(night.nightId)
}

sealed class DataItem {
    abstract val id: Long

    data class SleepNightItem(val item: SleepNight) : DataItem() {
        override val id: Long
            get() = item.nightId
    }

    data object Header : DataItem() {
        override val id = Long.MIN_VALUE
    }


}