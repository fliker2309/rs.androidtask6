package com.example.androidtask6.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.androidtask6.R
import com.example.androidtask6.data.entities.Song
import com.example.androidtask6.databinding.ListItemBinding


//typealias?
interface SongActionListener {
    fun onSongClick(song: Song)
}

class PlaylistAdapter(private val actionListener: SongActionListener) :
    RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>(), View.OnClickListener {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        return PlaylistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val song = songs[position]
        with(holder.binding) {
            holder.itemView.tag = song
            tvPrimary.text = song.title
            tvSecondary.text = song.artist
          Glide.with(ivItemImage.context)
              .load(song.bitmapUri)
              .placeholder(R.drawable.ic_image)
              .error(R.drawable.ic_image)
              .into(ivItemImage)
        }
    }

    override fun getItemCount(): Int = songs.size

    class PlaylistViewHolder(val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    private val diffCallBack = object : DiffUtil.ItemCallback<Song>() {
        override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    var songs: List<Song>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onClick(v: View) {
        val song = v.tag as Song
        actionListener.onSongClick(song)
    }


}
