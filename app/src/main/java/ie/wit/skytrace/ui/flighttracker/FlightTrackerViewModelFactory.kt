package ie.wit.skytrace.ui.flighttracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ie.wit.skytrace.model.repository.FlightTrackerRepository

class FlightTrackerViewModelFactory(private val repository: FlightTrackerRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlightTrackerViewModel::class.java)) {
            return FlightTrackerViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
