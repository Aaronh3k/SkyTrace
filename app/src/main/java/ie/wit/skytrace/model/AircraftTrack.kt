package ie.wit.skytrace.model

import com.google.gson.annotations.SerializedName

data class AircraftTrack(
    @SerializedName("icao24") val icao24: String,
    @SerializedName("callsign") val callsign: String,
    @SerializedName("startTime") val startTime: Double,
    @SerializedName("endTime") val endTime: Double,
    @SerializedName("path") val path: List<List<Any>>
)

data class Waypoint(
    val time: Int,
    val latitude: Float?,
    val longitude: Float?,
    val baroAltitude: Float?,
    val trueTrack: Float?,
    val onGround: Boolean
)
