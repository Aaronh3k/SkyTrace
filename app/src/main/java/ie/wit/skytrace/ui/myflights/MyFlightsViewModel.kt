package ie.wit.skytrace.ui.myflights

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import ie.wit.skytrace.model.MyFlight
import ie.wit.skytrace.model.repository.FlightTrackerRepository
import kotlinx.coroutines.launch
import android.util.Log
import ie.wit.skytrace.model.MyFlightDetails
import retrofit2.HttpException
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MyFlightsViewModel : ViewModel() {
    private val _trackedFlights = MutableLiveData<List<MyFlightDetails>>()
    val trackedFlights: LiveData<List<MyFlightDetails>> get() = _trackedFlights

    private val repository = FlightTrackerRepository()

    init {
        viewModelScope.launch {
            val flights = fetchTrackedFlights()
            val flightsWithDetails = mutableListOf<MyFlightDetails>()

            for (flight in flights) {
                try {
                    val flightRoute = repository.getFlightRoute(flight.callsign)
                    val aircraftMetadata = repository.getAircraftMetadata(flight.icao24)

                    val updatedFlight = MyFlightDetails(
                        icao24 = flight.icao24,
                        callsign = flight.callsign,
                        route = flightRoute.route,
                        manufacturerName = aircraftMetadata.manufacturerName,
                        model = aircraftMetadata.model,
                        owner = aircraftMetadata.owner,
                        operator = aircraftMetadata.operator
                    )
                    flightsWithDetails.add(updatedFlight)
                } catch (exception: HttpException) {
                    if (exception.code() == 404) {
                        val unavailableFlight = MyFlightDetails(
                            icao24 = flight.icao24,
                            callsign = flight.callsign,
                            route = listOf("Info Unavailable"),
                            manufacturerName = "Info Unavailable",
                            model = "Info Unavailable",
                            owner = "Info Unavailable",
                            operator = "Info Unavailable"
                        )
                        flightsWithDetails.add(unavailableFlight)
                    } else {

                    }
                }
            }
            _trackedFlights.value = flightsWithDetails
        }
    }

    private suspend fun fetchTrackedFlights(): List<MyFlight> = suspendCoroutine { continuation ->
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            val db = FirebaseFirestore.getInstance()
            val flightsRef = db.collection("users").document(userId).collection("tracked_flights")
            flightsRef.get().addOnSuccessListener { documents ->
                val flights = documents.mapNotNull { doc -> doc.toObject(MyFlight::class.java) }
                continuation.resume(flights)
            }.addOnFailureListener { exception ->
                Log.w(TAG, "Error getting tracked flights: ", exception)
                continuation.resume(emptyList())
            }
        } else {
            continuation.resume(emptyList())
        }
    }

    companion object {
        private const val TAG = "MyFlightsViewModel"
    }
}