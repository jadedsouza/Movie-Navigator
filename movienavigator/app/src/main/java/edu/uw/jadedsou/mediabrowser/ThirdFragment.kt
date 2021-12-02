package edu.uw.jadedsou.mediabrowser

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * A simple [Fragment] subclass as the third destination in the navigation.
 */
//to take from first fraggie
class ThirdFragment : Fragment() {
    private var relatedToTitle: String? = null
    private var movieID: Int? = null

    private lateinit var viewModel: ThirdViewModel
    private lateinit var adapter: MovieAdapter
    private lateinit var movieObserver: Observer<List<Movie>>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args: ThirdFragmentArgs by navArgs()
        movieID = args.query
        relatedToTitle = args.title

        viewModel = ViewModelProvider(this).get(ThirdViewModel::class.java)

        movieObserver = Observer<List<Movie>>{
            adapter.submitList(it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView =  inflater.inflate(R.layout.fragment_third, container, false)

        val btnHome = rootView.findViewById<Button>(R.id.btn_home)
        btnHome.setOnClickListener{
            var action = ThirdFragmentDirections.actionToFirstFragment()
            findNavController().navigate(action)
        }

        viewModel.movieData.observe(viewLifecycleOwner, movieObserver)
        adapter = MovieAdapter(findNavController(), viewModel.movieData)

        val txtRelatedTo: TextView = rootView.findViewById(R.id.txt_related_to)
        "Movies similar to $relatedToTitle".also { txtRelatedTo.text = it }

        val recycler = rootView.findViewById<RecyclerView>(R.id.recycler_results_list)
        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = adapter
        Log.e("getting mroe movie ID", movieID.toString())
        relatedToTitle?.let { Log.e("getting more movie title", it) }
        viewModel.getMoreMovies(movieID!!)

        return rootView
    }
}