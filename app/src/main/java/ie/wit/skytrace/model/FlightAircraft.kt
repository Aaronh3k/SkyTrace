package ie.wit.skytrace.model

data class FlightAircraft(
    val icao24: String,
    val firstSeen: Int,
    val estDepartureAirport: String?,
    val lastSeen: Int,
    val estArrivalAirport: String?,
    val callsign: String?,
    val estDepartureAirportHorizDistance: Int,
    val estDepartureAirportVertDistance: Int,
    val estArrivalAirportHorizDistance: Int,
    val estArrivalAirportVertDistance: Int,
    val departureAirportCandidatesCount: Int,
    val arrivalAirportCandidatesCount: Int
)