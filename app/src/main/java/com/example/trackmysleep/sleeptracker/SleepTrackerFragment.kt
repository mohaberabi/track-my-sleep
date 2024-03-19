package com.example.trackmysleep.sleeptracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trackmysleep.R
import com.example.trackmysleep.database.AppDatabase
import com.example.trackmysleep.databinding.FragmentSleepTrackerBinding


class SleepTrackerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSleepTrackerBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_sleep_tracker,
                container,
                false
            )

        val application = requireNotNull(this.activity).application

        val dao = AppDatabase.getInstance(application).sleepNighDao

        val viewModelFactory = SleepTrackerViewModelFactory(application, dao)


        val viewModel =
            ViewModelProviders.of(this, viewModelFactory)[SleepTrackerViewModel::class.java]



        binding.sleepTrackerViewModel = viewModel


        binding.lifecycleOwner = this

        viewModel.navigateToSleepQuality.observe(viewLifecycleOwner) {

                night ->
            night?.let {
                this.findNavController()
                    .navigate(
                        SleepTrackerFragmentDirections.actionSleepTrackerFragmentToSleepQualityFragment(
                            it.nightId
                        )

                    )
                viewModel.doneNavigation()
            }
        }

        val adapter = SleepNightAdapter(
            SleepNightListener { nightId ->

                viewModel.onSleepNightClick(nightId)
            }
        )



        binding.sleepList.adapter = adapter

        binding.sleepList.layoutManager = LinearLayoutManager(context)
        viewModel.nights.observe(viewLifecycleOwner) {
            adapter.addHeader(it)
        }
//        viewModel.navigateToSleepQualityWithId.observe(viewLifecycleOwner) {
//
//            it?.let {
//                findNavController().navigate(
//                    SleepTrackerFragmentDirections.actionSleepTrackerFragmentToSleepQualityFragment(
//                        it
//                    )
//                )
//                viewModel.onDoneGettingIdWithNavigation()
//            }
//
//        }
        return binding.root
    }
}