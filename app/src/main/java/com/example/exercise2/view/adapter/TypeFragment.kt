package com.example.exercise2.view.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exercise2.databinding.DisplayFragmentLayoutBinding
import com.example.exercise2.model.remote.MusicInfo
import com.example.exercise2.model.remote.MusicResponse

class TypeFragment: Fragment() {

    companion object{
        const val TYPE = "TYPE"
        fun newInstance(musicResponse: MusicResponse): TypeFragment {
            val fragment = TypeFragment()
            val bundle = Bundle()
            bundle.putParcelable(TYPE, musicResponse)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var binding: DisplayFragmentLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DisplayFragmentLayoutBinding.inflate(
            inflater,
            container,
            false
        )
        initViews()
        arguments?.getParcelable<MusicResponse>(TYPE)?.let {
            updateAdapter(it)
        }
        return binding.root
    }

    private fun updateAdapter(dataSet: MusicResponse) {
        binding.rvItunesResults.adapter = ItunesAdapter(parseListItunesResponse(dataSet)) {

        }
    }

    private fun parseListItunesResponse(dataSet: MusicResponse): List<MusicInfo> {
        return dataSet.results.map { song ->
            MusicInfo(
                song.trackName,
                song.artistName,
                song.currency,
                song.previewUrl,
                song.artworkUrl100,
                song.trackPrice
            )
        }
    }

    private fun initViews() {
        binding.rvItunesResults.layoutManager = LinearLayoutManager(context)
    }
}