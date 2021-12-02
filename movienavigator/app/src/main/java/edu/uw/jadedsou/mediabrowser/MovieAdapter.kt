package edu.uw.jadedsou.mediabrowser

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import edu.uw.jadedsou.mediabrowser.network.MovieApi
import edu.uw.jadedsou.mediabrowser.network.ResponseResults
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MovieAdapter(val nc: NavController, var data : MutableLiveData<List<Movie>>): androidx.recyclerview.widget.ListAdapter<Movie, MovieAdapter.ViewHolder>(MovieDiffCallback()){

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val movieDisplay: TextView = view.findViewById<TextView>(R.id.txt_display)
        val gridItem: LinearLayout = view.findViewById(R.id.grid_item)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(
            R.layout.grid_item,
            parent,
            false
        )
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.movieDisplay.text = item.title

        holder.gridItem.setOnClickListener {
            var action = FirstFragmentDirections.actionToSecondFragment(item)
            nc.navigate(action)
        }

    }

    fun filter(text: String) {
        if (text.isNotBlank()) {
            MovieApi.RETROFIT_SERVICE.searchMovies(text).enqueue(object :
                Callback<ResponseResults> {
                override fun onResponse(
                    call: Call<ResponseResults>,
                    response: Response<ResponseResults>
                ) {
                    val body = response.body()
                    val movies = body!!.results

                    data.value = movies //updating

                }

                override fun onFailure(call: Call<ResponseResults>, t: Throwable) {
                    Log.e(ContentValues.TAG, "Failure: ${t.message}")
                }
            })
            this.notifyDataSetChanged()
        }
    }
}