package ivan.gorbunov.testglobars.model.retrofit

import ivan.gorbunov.testglobars.model.retrofit.data.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "https://test.globars.ru/api/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()


interface GlobarApiService {
    @POST("auth/login")
    suspend fun getToken(@Body user: UserLogin) : Token

    @GET("tracking/sessions")
    suspend fun getSession(@Header("Authorization") token: String): Session

    @POST("tracking/{sessionId}/units")
    suspend fun getUnits(@Path("sessionId") session: String, @Query("mobile") boolean: Boolean = true): Units
}

object GlobarApi {
    val retrofitService: GlobarApiService by lazy {
        retrofit.create(GlobarApiService::class.java)
    }
}



