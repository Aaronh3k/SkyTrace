package ie.wit.skytrace.ui.myflights

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ie.wit.skytrace.R
import ie.wit.skytrace.model.MyFlightDetails

class MyFlightAdapter(private var flights: List<MyFlightDetails>, private val onUntrackClickListener: (MyFlightDetails) -> Unit) :
    RecyclerView.Adapter<MyFlightAdapter.FlightViewHolder>() {

    inner class FlightViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCallsign: TextView = itemView.findViewById(R.id.tv_callsign)
        val tvIcao24: TextView = itemView.findViewById(R.id.tv_icao24)
        val tvRoute: TextView = itemView.findViewById(R.id.tv_route)
        val tvManufacturer: TextView = itemView.findViewById(R.id.tv_manufacturer)
        val tvModel: TextView = itemView.findViewById(R.id.tv_model)
        val tvOwner: TextView = itemView.findViewById(R.id.tv_owner)
        val tvOperator: TextView = itemView.findViewById(R.id.tv_operator)
        val btnUntrack: Button = itemView.findViewById(R.id.btn_untrack)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.flight_item, parent, false)
        return FlightViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FlightViewHolder, position: Int) {
        val flight = flights[position]

        holder.tvCallsign.text = "Callsign: ${flight.callsign}"
        holder.tvIcao24.text = "ICAO24: ${flight.icao24}"
        holder.tvRoute.text = "Flight Route: ${flight.route.joinToString(" ✈️")}"
        holder.tvManufacturer.text = "Manufacturer: ${flight.manufacturerName}"
        holder.tvModel.text = "Model: ${flight.model}"
        holder.tvOwner.text = "Owner: ${flight.owner}"
        holder.tvOperator.text = "Operator: ${flight.operator}"

        holder.btnUntrack.setOnClickListener {
            onUntrackClickListener(flight)
        }
    }

    override fun getItemCount(): Int = flights.size

    fun updateFlights(newFlights: List<MyFlightDetails>) {
        flights = newFlights
        notifyDataSetChanged()
    }
}