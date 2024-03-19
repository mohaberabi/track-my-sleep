package com.example.trackmysleep.sleepquality

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.trackmysleep.R
import com.example.trackmysleep.database.AppDatabase
import com.example.trackmysleep.databinding.FragmentSleepQualityBinding


class SleepQualityFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSleepQualityBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_sleep_quality,
                container,
                false,
            )
        val application = requireNotNull(this.activity).application

        val dao = AppDatabase.getInstance(application).sleepNighDao

        val args = SleepQualityFragmentArgs.fromBundle(requireArguments())

        val factory = SleepQulaityViewModelFactory(args.sleepNightId, dao)
        val viewModel = ViewModelProviders.of(this, factory)[SleepQualityViewModel::class.java]
        binding.lifecycleOwner = this
        binding.sleepQualityViewModel = viewModel
        viewModel.navigateToTracker.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(SleepQualityFragmentDirections.actionSleepQualityFragmentToSleepTrackerFragment())
                viewModel.doneNaivgating()
            }
        }
        return binding.root
    }


}