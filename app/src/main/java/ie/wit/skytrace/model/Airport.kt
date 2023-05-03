package ie.wit.skytrace.model

data class Airport(
    val icao: String,
    val iata: String?,
    val name: String,
    val city: String,
    val state: String,
    val country: String,
    val elevation: Int,
    val lat: Double,
    val lon: Double,
    val tz: String
)
