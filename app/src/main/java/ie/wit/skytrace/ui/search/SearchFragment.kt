package ie.wit.skytrace.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ie.wit.skytrace.R
import ie.wit.skytrace.databinding.FragmentSearchBinding
import ie.wit.skytrace.ui.flightsintimeinterval.FlightsInTimeIntervalFragment

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        binding.flightsInLastHourButton.setOnClickListener {
            val flightsInTimeIntervalFragment = FlightsInTimeIntervalFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, flightsInTimeIntervalFragment)
                .addToBackStack(null)
                .commit()
        }

        // Set up onClickListeners for other buttons
        // ...

        return binding.root
    }
}