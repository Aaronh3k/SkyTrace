package ie.wit.skytrace.ui.flightsintimeinterval

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ie.wit.skytrace.R
import ie.wit.skytrace.model.AirportUtil
import ie.wit.skytrace.model.FlightsInTimeInterval
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FlightsInTimeIntervalAdapter(private val flights: List<FlightsInTimeInterval>, private val context: Context) :
    RecyclerView.Adapter<FlightsInTimeIntervalAdapter.FlightsInTimeIntervalViewHolder>() {

    private val airportUtil = AirportUtil(context)

    inner class FlightsInTimeIntervalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val flightIcao24: TextView = itemView.findViewById(R.id.flight_icao24)
        val flightCallsign: TextView = itemView.findViewById(R.id.flight_callsign)
        val flightFirstSeen: TextView = itemView.findViewById(R.id.flight_firstSeen)
        val flightEstDepartureAirport: TextView = itemView.findViewById(R.id.flight_estDepartureAirport)
        val flightLastSeen: TextView = itemView.findViewById(R.id.flight_lastSeen)
        val flightEstArrivalAirport: TextView = itemView.findViewById(R.id.flight_estArrivalAirport)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightsInTimeIntervalViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_flight_in_time_interval, parent, false)
        return FlightsInTimeIntervalViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FlightsInTimeIntervalViewHolder, position: Int) {
        val currentItem = flights[position]
        holder.flightIcao24.text = "ICAO24: ${currentItem.icao24}"
        holder.flightCallsign.text = "Callsign: ${currentItem.callsign}"
        holder.flightFirstSeen.text = "First Seen: ${convertUnixToHumanReadable(currentItem.firstSeen)}"
        holder.flightEstDepartureAirport.text = "Est. Departure Airport: ${airportUtil.getFullAirportName(currentItem.estDepartureAirport)}"
        holder.flightLastSeen.text = "Last Seen: ${convertUnixToHumanReadable(currentItem.lastSeen)}"
        holder.flightEstArrivalAirport.text = "Est. Arrival Airport: ${airportUtil.getFullAirportName(currentItem.estArrivalAirport)}"
    }

    private fun convertUnixToHumanReadable(unixSeconds: Int): String {
        val date = Date(unixSeconds * 1000L)
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return sdf.format(date)
    }

    override fun getItemCount(): Int {
        return flights.size
    }
}