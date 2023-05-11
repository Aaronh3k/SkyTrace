package ie.wit.skytrace.model

data class MyFlightDetails(
    val icao24: String,
    val callsign: String,
    val route: List<String>,
    val manufacturerName: String?,
    val model: String?,
    val owner: String?,
    val operator: String?
)