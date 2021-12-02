package edu.uw.jadedsou.mediabrowser

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.uw.jadedsou.mediabrowser.network.MovieApi
import edu.uw.jadedsou.mediabrowser.network.ResponseResults
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FirstViewModel: ViewModel(){
    private var _movieData = MutableLiveData<List<Movie>>()

    val movieData: MutableLiveData<List<Movie>>
        get() = _movieData

    fun getNewMovies(){
        MovieApi.RETROFIT_SERVICE.getNewMovies().enqueue(object: Callback<ResponseResults>{
            override fun onResponse(call: Call<ResponseResults>, response: Response<ResponseResults>) {
                val body = response.body()
                val movies = body!!.results //null check
                _movieData.value = movies
            }
            override fun onFailure(call: Call<ResponseResults>, t: Throwable) {
                Log.e(ContentValues.TAG, "Failure: ${t.message}")
            }
        })
    }
}