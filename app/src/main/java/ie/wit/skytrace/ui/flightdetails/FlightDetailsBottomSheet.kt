package ie.wit.skytrace.ui.flightdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.gms.maps.model.Marker
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ie.wit.skytrace.R
import ie.wit.skytrace.model.FlightState

class FlightDetailsBottomSheet(private val marker: Marker) : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_flight_details, container, false)
        val flightState = marker.tag as? FlightState

        view.findViewById<TextView>(R.id.callsign_text).text = flightState?.callsign
        view.findViewById<TextView>(R.id.icao24_text).text = flightState?.icao24
        view.findViewById<TextView>(R.id.origin_country_text).text = flightState?.originCountry
        view.findViewById<TextView>(R.id.latitude_text).text = flightState?.latitude?.toString()
        view.findViewById<TextView>(R.id.longitude_text).text = flightState?.longitude?.toString()
        view.findViewById<TextView>(R.id.baro_altitude_text).text = flightState?.baroAltitude?.toString()
        view.findViewById<TextView>(R.id.velocity_text).text = flightState?.velocity?.toString()
        view.findViewById<TextView>(R.id.true_track_text).text = flightState?.trueTrack?.toString()
        view.findViewById<TextView>(R.id.vertical_rate_text).text = flightState?.verticalRate?.toString()
        view.findViewById<TextView>(R.id.geo_altitude_text).text = flightState?.geoAltitude?.toString()
        view.findViewById<TextView>(R.id.squawk_text).text = flightState?.squawk

        return view
    }
}
