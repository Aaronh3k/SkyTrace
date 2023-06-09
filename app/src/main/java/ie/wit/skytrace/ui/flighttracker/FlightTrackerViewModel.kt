package ie.wit.skytrace.ui.flighttracker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ie.wit.skytrace.model.AircraftMetadata
import ie.wit.skytrace.model.FlightRoute
import ie.wit.skytrace.model.FlightState
import ie.wit.skytrace.model.repository.FlightTrackerRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class FlightTrackerViewModel(private val repository: FlightTrackerRepository) : ViewModel() {

    private val _flightStates = MutableLiveData<List<FlightState>>()
    val flightStates: LiveData<List<FlightState>>
        get() = _flightStates
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    private val _aircraftMetadata = MutableLiveData<AircraftMetadata>()
    val aircraftMetadata: LiveData<AircraftMetadata> = _aircraftMetadata

    private val _flightRoutes = MutableLiveData<Map<String, FlightRoute>>()
    val flightRoutes: LiveData<Map<String, FlightRoute>>
        get() = _flightRoutes

    init {
        _flightRoutes.value = emptyMap()
    }

    fun fetchFlightStates(
        time: Int? = null,
        icao24: List<String>? = null,
        lamin: Float? = null,
        lomin: Float? = null,
        lamax: Float? = null,
        lomax: Float? = null,
        extended: Int? = null
    ) {
        viewModelScope.launch {
            try {
                val result = repository.getFlightStates(
                    time = time,
                    icao24 = icao24,
                    lamin = lamin,
                    lomin = lomin,
                    lamax = lamax,
                    lomax = lomax,
                    extended = extended
                )
                println(result)
                _flightStates.value = result.states.map { stateList ->
                    FlightState(
                        icao24 = stateList[0] as String,
                        callsign = stateList[1] as String,
                        originCountry = stateList[2] as String,
                        timePosition = (stateList[3] as Number?)?.toLong(),
                        lastContact = (stateList[4] as Number).toLong(),
                        longitude = stateList[5] as Double?,
                        latitude = stateList[6] as Double?,
                        baroAltitude = stateList[7] as Double?,
                        onGround = stateList[8] as Boolean,
                        velocity = stateList[9] as Double?,
                        trueTrack = stateList[10] as Double?,
                        verticalRate = stateList[11] as Double?,
                        sensors = stateList[12] as List<Int>?,
                        geoAltitude = stateList[13] as Double?,
                        squawk = stateList[14] as String?,
                        spi = stateList[15] as Boolean,
                        positionSource = (stateList[16] as Number).toInt()
                    )
                }
            } catch (e: HttpException) {
                if (e.code() == 429) {
                    _errorMessage.value = "Too many requests. Please try again later."
                } else {
                    _errorMessage.value = "An HTTP error occurred: ${e.message}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "An error occurred: ${e.message}"
            }
        }
    }

    fun getFlightRoute(callsign: String): LiveData<FlightRoute>{
        val currentRoutes = _flightRoutes.value ?: emptyMap()

        if (!currentRoutes.containsKey(callsign)) {
            viewModelScope.launch {
                try {
                    val result = repository.getFlightRoute(callsign)
                    println(result)
                    _flightRoutes.value = currentRoutes + (callsign to result)
                } catch (e: HttpException) {
                    // Handle HTTP exceptions here
                } catch (e: Exception) {
                    // Handle other exceptions here
                }
            }
        }

        return MutableLiveData(currentRoutes[callsign])
    }

    fun getAircraftMetadata(icao24: String) {
        viewModelScope.launch {
            try {
                val metadata = repository.getAircraftMetadata(icao24)
                _aircraftMetadata.value = metadata
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun onErrorMessageDisplayed() {
        _errorMessage.value = null
    }
}
