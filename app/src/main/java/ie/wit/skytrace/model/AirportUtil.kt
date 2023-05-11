package ie.wit.skytrace.model

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.io.InputStreamReader

class AirportUtil(private val context: Context) {

    private var airports: Map<String, Airport>? = null

    init {
        loadAirportData()
    }

    private fun loadAirportData() {
        if (airports != null) {
            return
        }

        try {
            InputStreamReader(context.assets.open("airports.json")).use { reader ->
                val type = object : TypeToken<Map<String, Airport>>() {}.type
                val airportMap: Map<String, Airport> = Gson().fromJson(reader, type)
                airports = airportMap
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
            throw RuntimeException("Error loading airport data.", ex)
        }
    }

    fun getAirport(icao: String?): Airport? {
        if (icao == null) return null

        return airports?.get(icao)
    }

    fun getFullAirportName(icao: String?): String {
        val airport = getAirport(icao)
        return airport?.let {
            "${it.name}, ${it.city}, ${it.state}, ${it.country}"
        } ?: icao ?: "Unknown"
    }
}
