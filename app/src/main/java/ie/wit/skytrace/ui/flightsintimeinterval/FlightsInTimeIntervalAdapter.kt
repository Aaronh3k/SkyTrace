import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ie.wit.skytrace.R
import ie.wit.skytrace.model.FlightsInTimeInterval

class FlightsInTimeIntervalAdapter(private val flights: List<FlightsInTimeInterval>) :
    RecyclerView.Adapter<FlightsInTimeIntervalAdapter.FlightsInTimeIntervalViewHolder>() {

    inner class FlightsInTimeIntervalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val flightIcao24: TextView = itemView.findViewById(R.id.flight_icao24)
        val flightCallsign: TextView = itemView.findViewById(R.id.flight_callsign)
        val flightFirstSeen: TextView = itemView.findViewById(R.id.flight_firstSeen) // Changed from flightTimestamp to flightFirstSeen
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightsInTimeIntervalViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_flight_in_time_interval, parent, false)
        return FlightsInTimeIntervalViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FlightsInTimeIntervalViewHolder, position: Int) {
        val currentItem = flights[position]
        holder.flightIcao24.text = currentItem.icao24
        holder.flightCallsign.text = currentItem.callsign
        holder.flightFirstSeen.text = currentItem.firstSeen.toString() // Changed from currentItem.timestamp to currentItem.firstSeen
    }

    override fun getItemCount(): Int {
        return flights.size
    }
}