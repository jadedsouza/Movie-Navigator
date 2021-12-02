package edu.uw.jadedsou.mediabrowser.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import edu.uw.jadedsou.mediabrowser.Movie
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.themoviedb.org/3/"

private const val API_KEY = "?api_key=1ef1a3032e631e5c17456a034d14916d"


interface MovieApiService{

    @GET("movie/now_playing${API_KEY}")
    fun getNewMovies() : Call<ResponseResults>


    @GET("search/movie${API_KEY}")
    fun searchMovies(@Query("query")query: String): Call<ResponseResults>


    @GET("movie/{movie_id}/similar${API_KEY}")
    fun getMoreMovies(@Path("movie_id") movieID: Int): Call<ResponseResults>
}


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

object MovieApi{
    val RETROFIT_SERVICE: MovieApiService by lazy {
        retrofit.create(MovieApiService::class.java)
    }
}

data class ResponseResults (val results: List<Movie>)