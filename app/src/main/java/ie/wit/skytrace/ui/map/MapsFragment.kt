package ie.wit.skytrace.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import ie.wit.skytrace.R
import ie.wit.skytrace.databinding.FragmentMapsBinding
import ie.wit.skytrace.model.FlightState
import ie.wit.skytrace.model.repository.FlightTrackerRepository
import ie.wit.skytrace.ui.flighttracker.FlightTrackerViewModel
import ie.wit.skytrace.ui.flighttracker.FlightTrackerViewModelFactory
import ie.wit.skytrace.ui.maptype.MapTypeBottomSheetFragment

class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val locationPermissionCode = 1000
    private lateinit var mapsViewModel: MapsViewModel
    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!
    private lateinit var bottomBar: BottomNavigationView
    private lateinit var flightTrackerViewModel: FlightTrackerViewModel
    private val markers = mutableListOf<Marker>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionCode
            )
        } else {
            getLastLocation()
        }

        mapsViewModel = ViewModelProvider(this)[MapsViewModel::class.java]
        mapsViewModel.currentLatLng.observe(viewLifecycleOwner) { currentLatLng ->
            updateMapLocation(currentLatLng)
            fetchFlightStates(currentLatLng)
        }

        binding.fabMapType.setOnClickListener {
            val mapTypeBottomSheetFragment = MapTypeBottomSheetFragment { selectedMapType ->
                setMapType(selectedMapType)
            }
            mapTypeBottomSheetFragment.show(childFragmentManager, "MapTypeBottomSheetFragment")
        }

        // Remove bottom bar initialization and usage
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
// Update the MapsViewModel with the current location
                mapsViewModel.updateLocation(location)
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Enable zoom controls
        mMap.uiSettings.isZoomControlsEnabled = true

        // Enable scrolling gestures
        mMap.uiSettings.isScrollGesturesEnabled = true

        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mMap.isMyLocationEnabled = true
    }

    // Update the map location when the currentLatLng LiveData is updated
    private fun updateMapLocation(latLng: LatLng) {
        mMap.clear()
        mMap.addMarker(MarkerOptions().position(latLng).title("My Location"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
    }

    private fun fetchFlightStates(currentLatLng: LatLng) {
        val flightTrackerViewModelFactory = FlightTrackerViewModelFactory(
            FlightTrackerRepository()
        )
        flightTrackerViewModel = ViewModelProvider(
            this,
            flightTrackerViewModelFactory
        )[FlightTrackerViewModel::class.java]

        val latDiff = 0.5
        val lonDiff = 0.5
        println((currentLatLng.latitude - latDiff).toFloat())
        println((currentLatLng.longitude - lonDiff).toFloat())
        println((currentLatLng.latitude + latDiff).toFloat())
        println((currentLatLng.longitude + lonDiff).toFloat())
        flightTrackerViewModel.fetchFlightStates(
            lamin = (currentLatLng.latitude - latDiff).toFloat(),
            lomin = (currentLatLng.longitude - lonDiff).toFloat(),
            lamax = (currentLatLng.latitude + latDiff).toFloat(),
            lomax = (currentLatLng.longitude + lonDiff).toFloat()
        )

        flightTrackerViewModel.flightStates.observe(viewLifecycleOwner, Observer { flightStates ->
            updateFlightMarkers(flightStates)
        })
    }

    private fun updateFlightMarkers(flightStates: List<FlightState>) {
        markers.forEach { it.remove() }
        markers.clear()

        flightStates.forEach { flightState ->
            flightState.latitude?.let { latitude ->
                flightState.longitude?.let { longitude ->
                    val position = LatLng(latitude, longitude)
                    val markerOptions = MarkerOptions()
                        .position(position)
                        .title(flightState.callsign)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_flight_marker))
                    mMap.addMarker(markerOptions)?.let { markers.add(it) }
                }
            }
        }
    }

    private fun setMapType(mapType: Int) {
        mMap.mapType = mapType
    }
}
