package ie.wit.skytrace.ui.flighttracker

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ie.wit.skytrace.databinding.FragmentFlightTrackerBinding
import ie.wit.skytrace.model.repository.FlightTrackerRepository
import com.google.android.material.snackbar.Snackbar
import ie.wit.skytrace.R

class FlightTrackerFragment : Fragment(), OnMapReadyCallback {

    private lateinit var flightTrackerViewModel: FlightTrackerViewModel
    private lateinit var map: GoogleMap
    private var _binding: FragmentFlightTrackerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFlightTrackerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Initialize the FlightTrackerViewModel
        val repository = FlightTrackerRepository()
        val viewModelFactory = FlightTrackerViewModelFactory(repository)
        flightTrackerViewModel = ViewModelProvider(this, viewModelFactory)[FlightTrackerViewModel::class.java]

        flightTrackerViewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            message?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                flightTrackerViewModel.onErrorMessageDisplayed()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        flightTrackerViewModel.flightStates.observe(viewLifecycleOwner) { flightStates ->
            flightStates.forEach { flightState ->
                flightState.latitude?.let { latitude ->
                    flightState.longitude?.let { longitude ->
                        val markerIcon = resizedBitmapDescriptor(R.drawable.ic_flight_marker, 24, 24)
                        println(flightState.trueTrack)
                        val rotation = flightState.trueTrack?.toFloat() ?: 0f
                        val markerOptions = MarkerOptions()
                            .position(LatLng(latitude, longitude))
                            .icon(markerIcon)
                            .rotation(rotation)
                            .anchor(0.5f, 0.5f)

                        map.addMarker(markerOptions)
                    }
                }
            }
        }
    }

    private fun resizedBitmapDescriptor(resourceId: Int, width: Int, height: Int): BitmapDescriptor {
        val drawable = ContextCompat.getDrawable(requireContext(), resourceId)
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable!!.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
