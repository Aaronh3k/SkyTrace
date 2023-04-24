package ie.wit.skytrace.model.repository

import ie.wit.skytrace.model.FlightStateData
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://opensky-network.org/"

class AddCookieInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()

        val cookie = "_gid=GA1.2.95239262.1682286101; reDimCookieHint=1; joomla_user_state=logged_in; 910fe6bd4787703d58ff06cf8b5708ff=kgssaqap1hukko8eq7q0nclh8n; joomla_remember_me_46453b3ce631ae1e245f8a9371f44911=Zxj5159M8CxxgLsG.MoAHz7lajlEZouxI21gF; _ga_R4BXH0SS4N=GS1.1.1682335781.4.1.1682335894.0.0.0; _ga=GA1.1.1107229751.1682286101; XSRF-TOKEN=6cedd40d-1600-445b-b9d9-7cde28578d74"

        requestBuilder.addHeader("Cookie", cookie)

        val newRequest = requestBuilder.build()
        return chain.proceed(newRequest)
    }
}

private val client: OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    .addInterceptor(AddCookieInterceptor())
    .build()

private val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()

interface OpenSkyApi {
    @GET("api/states/all")
    suspend fun getFlightStates(
        @Query("time") time: Int? = null,
        @Query("icao24") icao24: String? = null,
        @Query("lamin") lamin: Float? = null,
        @Query("lomin") lomin: Float? = null,
        @Query("lamax") lamax: Float? = null,
        @Query("lomax") lomax: Float? = null,
        @Query("extended") extended: Int? = null
    ): FlightStateData
}

class FlightTrackerRepository {
    private val openSkyApi: OpenSkyApi = retrofit.create(OpenSkyApi::class.java)

    suspend fun getFlightStates(
        time: Int? = null,
        icao24: List<String>? = null,
        lamin: Float? = null,
        lomin: Float? = null,
        lamax: Float? = null,
        lomax: Float? = null,
        extended: Int? = null
    ): FlightStateData {
        return openSkyApi.getFlightStates(
            time = time,
            icao24 = icao24?.joinToString(","),
            lamin = lamin,
            lomin = lomin,
            lamax = lamax,
            lomax = lomax,
            extended = extended
        )
    }
}