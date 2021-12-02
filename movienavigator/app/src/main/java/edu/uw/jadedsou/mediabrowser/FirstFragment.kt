package edu.uw.jadedsou.mediabrowser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.uw.jadedsou.mediabrowser.databinding.FragmentFirstBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private lateinit var viewModel: FirstViewModel
    private lateinit var adapter: MovieAdapter
    private lateinit var movieObserver: Observer<List<Movie>>
    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(FirstViewModel::class.java)

        movieObserver = Observer<List<Movie>>{
            adapter.submitList(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.movieData.observe(viewLifecycleOwner, movieObserver)

        val search = view.findViewById<SearchView>(R.id.searchView)
        adapter = MovieAdapter(findNavController(), viewModel.movieData)
        val recycler = view.findViewById<RecyclerView>(R.id.recycler_list)

        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this.context) //was activity.

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null && p0.isNotBlank()) {
                    adapter.filter(p0)
                }
                return false
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                search.clearFocus()
                var arrayOne = arrayListOf<String>();
                for (i in adapter.currentList) {
                    arrayOne.add(i.title)
                }
                if(arrayOne.contains(p0)) {
                    if (p0 != null && p0.isNotBlank()) {
                        adapter.filter(p0)
                    } else {
                        Toast.makeText(context, "not found", Toast.LENGTH_LONG).show()
                    }
                }
                return false
            }
        })

        viewModel.getNewMovies() //could be below
}
}
