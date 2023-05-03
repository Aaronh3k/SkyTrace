package ie.wit.skytrace.ui.flightsintimeinterval

import FlightsInTimeIntervalAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.skytrace.databinding.FragmentFlightsInTimeIntervalBinding
import ie.wit.skytrace.model.repository.FlightTrackerRepository

class FlightsInTimeIntervalFragment : Fragment() {

    private lateinit var binding: FragmentFlightsInTimeIntervalBinding
    private lateinit var viewModel: FlightsInTimeIntervalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFlightsInTimeIntervalBinding.inflate(inflater, container, false)

        val repository = FlightTrackerRepository()
        val viewModelFactory = FlightsInTimeIntervalViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FlightsInTimeIntervalViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchFlightsInLastHour()
    }

    private fun fetchFlightsInLastHour() {
        val currentTime = System.currentTimeMillis()
        val beginTime = (currentTime - 3600000L) / 1000
        val endTime = currentTime / 1000

        viewModel.getFlightsInTimeInterval(beginTime.toInt(), endTime.toInt())

        viewModel.flightsInTimeInterval.observe(viewLifecycleOwner, Observer { flights ->
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerView.adapter = FlightsInTimeIntervalAdapter(flights)
        })
    }
}