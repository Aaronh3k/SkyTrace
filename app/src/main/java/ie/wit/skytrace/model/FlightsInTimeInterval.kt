package ie.wit.skytrace.model

import com.google.gson.annotations.SerializedName

data class FlightsInTimeInterval(
    @SerializedName("icao24") val icao24: String,
    @SerializedName("firstSeen") val firstSeen: Int,
    @SerializedName("estDepartureAirport") val estDepartureAirport: String?,
    @SerializedName("lastSeen") val lastSeen: Int,
    @SerializedName("estArrivalAirport") val estArrivalAirport: String?,
    @SerializedName("callsign") val callsign: String?,
    @SerializedName("estDepartureAirportHorizDistance") val estDepartureAirportHorizDistance: Int,
    @SerializedName("estDepartureAirportVertDistance") val estDepartureAirportVertDistance: Int,
    @SerializedName("estArrivalAirportHorizDistance") val estArrivalAirportHorizDistance: Int,
    @SerializedName("estArrivalAirportVertDistance") val estArrivalAirportVertDistance: Int,
    @SerializedName("departureAirportCandidatesCount") val departureAirportCandidatesCount: Int,
    @SerializedName("arrivalAirportCandidatesCount") val arrivalAirportCandidatesCount: Int
)
