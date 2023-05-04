package ie.wit.skytrace.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import ie.wit.skytrace.R
import ie.wit.skytrace.databinding.FragmentBottomNavigationBinding
import ie.wit.skytrace.ui.account.AccountFragment
import ie.wit.skytrace.ui.flightsintimeinterval.FlightsInTimeIntervalFragment
import ie.wit.skytrace.ui.map.MapsFragment
import ie.wit.skytrace.ui.myflights.MyFlightsFragment

class BottomNavigationFragment : Fragment() {

    private var _binding: FragmentBottomNavigationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomNavigationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapsFragment = MapsFragment()
        childFragmentManager.commit {
            replace(R.id.fragment_container, mapsFragment)
        }

        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_maps -> {
                    val mapsFragment = MapsFragment()
                    childFragmentManager.commit {
                        replace(R.id.fragment_container, mapsFragment)
                    }
                }
                R.id.action_search -> {
                    val searchFragment = FlightsInTimeIntervalFragment()
                    childFragmentManager.commit {
                        replace(R.id.fragment_container, searchFragment)
                        addToBackStack(null)
                    }
                }
                R.id.action_flight -> {
                    val myFlightsFragment = MyFlightsFragment()
                    childFragmentManager.commit {
                        replace(R.id.fragment_container, myFlightsFragment)
                        addToBackStack(null)
                    }
                }
                R.id.action_account -> {
                    val accountFragment = AccountFragment()
                    childFragmentManager.commit {
                        replace(R.id.fragment_container, accountFragment)
                        addToBackStack(null)
                    }
                }
            }
            true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}