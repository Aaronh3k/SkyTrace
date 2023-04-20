package ie.wit.skytrace.viewmodel

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapsViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext
    val currentLatLng = MutableLiveData<LatLng>()

    fun updateLocation(location: Location?) {
        location?.let {
            currentLatLng.postValue(LatLng(it.latitude, it.longitude))
        }
    }
}
