package ie.wit.skytrace.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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
import ie.wit.skytrace.databinding.InfoWindowFlightExtendedBinding
import ie.wit.skytrace.model.FlightState
import ie.wit.skytrace.model.repository.FlightTrackerRepository
import ie.wit.skytrace.ui.flightdetails.FlightDetailsBottomSheet
import ie.wit.skytrace.ui.flighttracker.FlightTrackerViewModel
import ie.wit.skytrace.ui.flighttracker.FlightTrackerViewModelFactory
import ie.wit.skytrace.ui.maptype.MapTypeBottomSheetFragment

class MapsFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnCameraIdleListener {

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
        }

        binding.fabMapType.setOnClickListener {
            val mapTypeBottomSheetFragment = MapTypeBottomSheetFragment { selectedMapType ->
                setMapType(selectedMapType)
            }
            mapTypeBottomSheetFragment.show(childFragmentManager, "MapTypeBottomSheetFragment")
        }

        bottomBar = binding.bottomNavigationView
        bottomBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_maps -> {
                    val mapsFragment = MapsFragment()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, mapsFragment)
                        .commit()
                }
                R.id.action_search -> {
                    // Navigate to Search screen
                }
                R.id.action_flight -> {
                    // Navigate to My Flight screen
                }
                R.id.action_account -> {
                    // Navigate to Account screen
                }
            }
            true
        }

        mapsViewModel.currentLatLng.observe(viewLifecycleOwner) { currentLatLng ->
            updateMapLocation(currentLatLng)
        }
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

        mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN

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

        mMap.setOnCameraIdleListener(this)

        mMap.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
            @SuppressLint("InflateParams")
            override fun getInfoWindow(marker: Marker): View {
                val flightState = marker.tag as? FlightState
                val inflater = requireActivity().layoutInflater
                val binding = InfoWindowFlightExtendedBinding.inflate(inflater, null, false)

                binding.callsignText.text = flightState?.callsign
                binding.originCountryText.text = flightState?.originCountry

                flightState?.callsign?.let { callsign ->
                    flightTrackerViewModel.getFlightRoute(callsign).observe(viewLifecycleOwner) { flightRoute ->
                        val routeText = flightRoute?.route?.joinToString(separator = " ✈️ ") ?: "Route unavailable"
                        binding.routeText.text = routeText
                    }
                }

                val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.info_window_animation)
                binding.root.startAnimation(animation)

                return binding.root
            }

            override fun getInfoContents(marker: Marker): View? {
                return null
            }
        })

        mMap.setOnInfoWindowClickListener { marker ->
            val flightState = marker.tag as? FlightState
            flightState?.let {
                val bottomSheet = FlightDetailsBottomSheet(marker)
                bottomSheet.show(childFragmentManager, "flightDetailsBottomSheet")
            }
        }
    }

    override fun onCameraIdle() {
        val visibleRegion = mMap.projection.visibleRegion.latLngBounds

        val lamin = visibleRegion.southwest.latitude.toFloat()
        val lomin = visibleRegion.southwest.longitude.toFloat()
        val lamax = visibleRegion.northeast.latitude.toFloat()
        val lomax = visibleRegion.northeast.longitude.toFloat()

        fetchFlightStates(lamin, lomin, lamax, lomax)
    }

    // Update the map location when the currentLatLng LiveData is updated
    private fun updateMapLocation(latLng: LatLng) {
        mMap.clear()
        mMap.addMarker(MarkerOptions().position(latLng).title("My Location"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
    }

    private fun fetchFlightStates(lamin: Float, lomin: Float, lamax: Float, lomax: Float) {
        val flightTrackerViewModelFactory = FlightTrackerViewModelFactory(
            FlightTrackerRepository()
        )
        flightTrackerViewModel = ViewModelProvider(
            this,
            flightTrackerViewModelFactory
        )[FlightTrackerViewModel::class.java]

        flightTrackerViewModel.fetchFlightStates(
            lamin = lamin,
            lomin = lomin,
            lamax = lamax,
            lomax = lomax
        )

        flightTrackerViewModel.flightStates.observe(viewLifecycleOwner) { flightStates ->
            updateFlightMarkers(flightStates)
        }
    }

    private fun updateFlightMarkers(flightStates: List<FlightState>) {
        markers.forEach { it.remove() }
        markers.clear()

        flightStates.forEach { flightState ->
            flightState.latitude?.let { latitude ->
                flightState.longitude?.let { longitude ->
                    val position = LatLng(latitude, longitude)
                    val angle = flightState.trueTrack?.toFloat() ?: 0f
                    val rotatedIcon = BitmapDescriptorFactory.fromBitmap(
                        rotateBitmap(
                            BitmapFactory.decodeResource(resources, R.drawable.ic_flight_marker),
                            angle
                        )
                    )
                    val markerOptions = MarkerOptions()
                        .position(position)
                        .title(flightState.callsign)
                        .icon(rotatedIcon)
                    mMap.addMarker(markerOptions)?.let {
                        markers.add(it)
                        it.tag = flightState
                    }
                }
            }
        }

        mMap.setOnMarkerClickListener { marker ->
            marker.showInfoWindow()
            true
        }
    }


    private fun rotateBitmap(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
    }

    private fun setMapType(mapType: Int) {
        mMap.mapType = mapType
    }
}
