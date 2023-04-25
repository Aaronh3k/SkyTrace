package ie.wit.skytrace.model

import com.google.gson.annotations.SerializedName

data class AircraftMetadata(
    @SerializedName("icao24")
    val icao24: String,
    @SerializedName("manufacturername")
    val manufacturerName: String?,
    @SerializedName("model")
    val model: String?,
    @SerializedName("owner")
    val owner: String?,
    @SerializedName("operator")
    val operator: String?,
    @SerializedName("reg")
    val registration: String?,
    @SerializedName("serialnumber")
    val serialNumber: String?,
    @SerializedName("built")
    val built: String?,
    @SerializedName("last_upgraded")
    val lastUpgraded: String?,
    @SerializedName("user_notes")
    val userNotes: String?,
)
