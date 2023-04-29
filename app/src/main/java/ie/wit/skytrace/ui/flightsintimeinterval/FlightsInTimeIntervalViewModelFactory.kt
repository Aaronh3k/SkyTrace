package ie.wit.skytrace.ui.flightsintimeinterval

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ie.wit.skytrace.model.repository.FlightTrackerRepository

class FlightsInTimeIntervalViewModelFactory(private val repository: FlightTrackerRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlightsInTimeIntervalViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FlightsInTimeIntervalViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}