package ie.wit.skytrace.ui.flightsintimeinterval

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ie.wit.skytrace.model.FlightsInTimeInterval
import ie.wit.skytrace.model.repository.FlightTrackerRepository
import kotlinx.coroutines.launch

class FlightsInTimeIntervalViewModel(private val repository: FlightTrackerRepository) : ViewModel() {

    val flightsInTimeInterval = MutableLiveData<List<FlightsInTimeInterval>>()

    fun getFlightsInTimeInterval(beginTime: Int, endTime: Int) {
        viewModelScope.launch {
            val result = repository.getFlightsInTimeInterval(beginTime, endTime)
            flightsInTimeInterval.postValue(result)
        }
    }
}
