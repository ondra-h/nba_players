import com.example.nba.data.api.BallDontLieApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object BallDontLieApiFactory {
    private const val BASE_URL = "https://api.balldontlie.io/v1/"

    fun create(client: OkHttpClient): BallDontLieApi {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(BallDontLieApi::class.java)
    }
}