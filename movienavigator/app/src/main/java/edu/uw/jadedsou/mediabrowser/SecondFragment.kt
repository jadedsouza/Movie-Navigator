package edu.uw.jadedsou.mediabrowser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {
    private var selectedMovie: Movie = Movie(1,"none", "none", "none")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args: SecondFragmentArgs by navArgs()
        selectedMovie = args.movie
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_second, container, false)
        val img: ImageView = rootView.findViewById<ImageView>(R.id.img)
        val title: TextView = rootView.findViewById(R.id.title)
        val dets: TextView = rootView.findViewById(R.id.dets)

        val btnSimilar: Button = rootView.findViewById<Button>(R.id.btn_similar)
        //do i need this
        dets.text = selectedMovie.plot
        title.text = selectedMovie.title

        Glide.with(this).load("https://image.tmdb.org/t/p/original" + selectedMovie.posterPath).into(img)


        btnSimilar.setOnClickListener {
            val action = SecondFragmentDirections.actionToThirdFragment(selectedMovie.id, selectedMovie.title)
            findNavController().navigate(action)
        }
        return rootView
    }
}