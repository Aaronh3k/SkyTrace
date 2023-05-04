package ie.wit.skytrace.ui.myflights

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.skytrace.databinding.FragmentMyFlightsBinding
import ie.wit.skytrace.model.MyFlightDetails

class MyFlightsFragment : Fragment() {
    private lateinit var viewModel: MyFlightsViewModel
    private var _binding: FragmentMyFlightsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MyFlightAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyFlightsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(MyFlightsViewModel::class.java)

        // Initialize RecyclerView Adapter and LayoutManager
        adapter = MyFlightAdapter(emptyList()) { flight ->
            viewModel.untrackFlight(flight as MyFlightDetails)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        // Observe LiveData and update UI
        viewModel.trackedFlights.observe(viewLifecycleOwner) { flights ->
            adapter.updateFlights(flights)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}