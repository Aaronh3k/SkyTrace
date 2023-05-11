package ie.wit.skytrace.model

import com.google.gson.annotations.SerializedName

data class FlightStateData(
    @SerializedName("states")
    val states: List<List<Any?>>,
    @SerializedName("time")
    val time: Long
)

data class FlightState(
    val icao24: String,
    val callsign: String,
    val originCountry: String,
    val timePosition: Long?,
    val lastContact: Long,
    val longitude: Double?,
    val latitude: Double?,
    val baroAltitude: Double?,
    val onGround: Boolean,
    val velocity: Double?,
    val trueTrack: Double?,
    val verticalRate: Double?,
    val sensors: List<Int>?,
    val geoAltitude: Double?,
    val squawk: String?,
    val spi: Boolean,
    val positionSource: Int
)
