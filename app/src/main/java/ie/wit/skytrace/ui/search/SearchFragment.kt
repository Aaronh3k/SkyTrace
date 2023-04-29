package ie.wit.skytrace.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import ie.wit.skytrace.R
import ie.wit.skytrace.ui.flightsintimeinterval.FlightsInTimeIntervalFragment

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Set up UI components and listeners here
        setUpFlightsInTimeIntervalFragment()
    }

    private fun setUpFlightsInTimeIntervalFragment() {
        val flightsInTimeIntervalFragment = FlightsInTimeIntervalFragment()
        val fragmentManager: FragmentManager = childFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.flightsInTimeIntervalContainer, flightsInTimeIntervalFragment)
        fragmentTransaction.commit()
    }
}
