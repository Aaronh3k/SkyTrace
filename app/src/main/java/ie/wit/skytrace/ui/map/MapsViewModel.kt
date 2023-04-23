package ie.wit.skytrace.ui.map

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class MapsViewModel : ViewModel() {

    private val _currentLatLng = MutableLiveData<LatLng>()
    val currentLatLng: LiveData<LatLng>
        get() = _currentLatLng

    fun updateLocation(location: Location) {
        _currentLatLng.value = LatLng(location.latitude, location.longitude)
    }
}
