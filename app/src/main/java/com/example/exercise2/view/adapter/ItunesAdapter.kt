package com.example.exercise2.view.adapter

import android.media.AudioManager
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exercise2.databinding.SongFragmentLayoutBinding
import com.example.exercise2.model.remote.MusicInfo
import com.squareup.picasso3.Picasso

class ItunesAdapter(private val dataSet: List<MusicInfo>,
                    private val openDetails: (MusicInfo) -> Unit): RecyclerView.Adapter<ItunesAdapter.ItunesHolder>(){

    class ItunesHolder(private val binding: SongFragmentLayoutBinding): RecyclerView.ViewHolder(binding.root){

        lateinit var mediaPlayer: MediaPlayer

        fun bind(currentElement: MusicInfo, openDetails: (MusicInfo) -> Unit) {
            mediaPlayer = MediaPlayer()
            binding.root.setOnClickListener {
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)

                try {
                    mediaPlayer.setDataSource(currentElement.previewUrl)
                    mediaPlayer.prepare()
                    mediaPlayer.start()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            binding.songTitle.text = currentElement.trackName
            binding.price.text = currentElement.trackPrice.toString()

            Picasso.Builder(itemView.context)
                .build()
                .load(currentElement.artworkUrl100)
                .into(binding.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItunesAdapter.ItunesHolder {
        return ItunesHolder(
            SongFragmentLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItunesAdapter.ItunesHolder, position: Int) {
        holder.bind(dataSet[position], openDetails)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}