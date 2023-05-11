package ie.wit.skytrace.model

import com.google.gson.annotations.SerializedName

data class FlightRoute(
    @SerializedName("callsign") val callsign: String,
    @SerializedName("route") val route: List<String>,
    @SerializedName("updateTime") val updateTime: Long,
    @SerializedName("operatorIata") val operatorIata: String,
    @SerializedName("flightNumber") val flightNumber: Int
)
