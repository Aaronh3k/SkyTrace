package ie.wit.skytrace.ui.flightdetails

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.maps.model.Marker
import com.google.firebase.firestore.FirebaseFirestore
import ie.wit.skytrace.databinding.BottomSheetFlightDetailsBinding
import ie.wit.skytrace.model.AircraftMetadata
import ie.wit.skytrace.model.FlightState
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ie.wit.skytrace.ui.map.MapsFragment

class FlightDetailsBottomSheet(
    private val marker: Marker,
    private val aircraftMetadata: AircraftMetadata,
    private val userId: String?
) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = BottomSheetFlightDetailsBinding.inflate(
            inflater, container, false
        )

        val flightState = marker.tag as? FlightState

        binding.callsignText.text = flightState?.callsign
        binding.icao24Text.text = flightState?.icao24
        binding.originCountryText.text = flightState?.originCountry
        binding.latitudeText.text = flightState?.latitude?.toString()
        binding.longitudeText.text = flightState?.longitude?.toString()
        binding.baroAltitudeText.text = flightState?.baroAltitude?.toString()
        binding.velocityText.text = flightState?.velocity?.toString()
        binding.trueTrackText.text = flightState?.trueTrack?.toString()
        binding.verticalRateText.text = flightState?.verticalRate?.toString()
        binding.geoAltitudeText.text = flightState?.geoAltitude?.toString()
        binding.squawkText.text = flightState?.squawk

        binding.manufacturerNameText.text = aircraftMetadata.manufacturerName ?: "info unavailable"
        binding.modelText.text = aircraftMetadata.model ?: "info unavailable"
        binding.ownerText.text = aircraftMetadata.owner ?: "info unavailable"
        binding.operatorText.text = aircraftMetadata.operator ?: "info unavailable"
        binding.registrationText.text = aircraftMetadata.registration ?: "info unavailable"
        binding.serialNumberText.text = aircraftMetadata.serialNumber ?: "info unavailable"
        binding.builtText.text = aircraftMetadata.built ?: "info unavailable"
        binding.lastUpgradedText.text = aircraftMetadata.lastUpgraded ?: "info unavailable"
        binding.userNotesText.text = aircraftMetadata.userNotes ?: "info unavailable"

        binding.trackFlightButton.setOnClickListener {
            flightState?.icao24?.let { icao24 ->
                flightState.callsign?.let { callsign ->
                    saveFlightDataToFirestore(icao24, callsign)
                }
            }
        }

        return binding.root
    }

    private fun saveFlightDataToFirestore(icao24: String, callsign: String) {
        val db = FirebaseFirestore.getInstance()

        val flightData = hashMapOf(
            "icao24" to icao24,
            "callsign" to callsign
        )

        if (userId != null) {
            db.collection("users").document(userId)
                .collection("tracked_flights")
                .add(flightData)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(requireContext(), "Flight tracked successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Error tracking flight", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        (parentFragment as? MapsFragment)?.isBottomSheetOpen = false
    }

}