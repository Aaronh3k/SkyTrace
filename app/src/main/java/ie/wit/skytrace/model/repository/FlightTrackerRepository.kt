package ie.wit.skytrace.model.repository

import ie.wit.skytrace.BuildConfig
import ie.wit.skytrace.model.FlightStateData
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://opensky-network.org/"

class AuthInterceptor(private val username: String, private val password: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
        val credentials = Credentials.basic(username, password)
        requestBuilder.addHeader("Authorization", credentials)
        val newRequest = requestBuilder.build()
        return chain.proceed(newRequest)
    }
}

private val client: OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    .addInterceptor(AuthInterceptor(BuildConfig.OPENSKY_USERNAME, BuildConfig.OPENSKY_PASSWORD))
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