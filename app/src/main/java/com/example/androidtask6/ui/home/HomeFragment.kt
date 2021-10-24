package com.example.androidtask6.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidtask6.R
import com.example.androidtask6.data.entities.Song
import com.example.androidtask6.databinding.FragmentHomeBinding
import com.example.androidtask6.other.Status
import com.example.androidtask6.presentation.MainViewModel
import com.example.androidtask6.ui.home.adapter.PlaylistAdapter
import com.example.androidtask6.ui.home.adapter.SongActionListener
import dagger.hilt.android.AndroidEntryPoint

const val TAG = "myTag"

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding)

    private val mainViewModel: MainViewModel =
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

    lateinit var playlistAdapter: PlaylistAdapter
 /*   private val playlistAdapter : PlaylistAdapter
    get() = binding.rvAllSongs.adapter as PlaylistAdapter*/

    /* override fun onCreateView(
         inflater: LayoutInflater,
         container: ViewGroup?,
         savedInstanceState: Bundle?
     ): View {
         _binding = FragmentHomeBinding.inflate(inflater, container, false)
         Log.d(TAG, "onCV")
         return binding.root
     }
 
     override fun onDestroy() {
         super.onDestroy()
         _binding = null
     }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")

        playlistAdapter = PlaylistAdapter(object : SongActionListener {
            override fun onSongClick(song: Song) {
                mainViewModel.playOrToggleSong(song)
            }
        })

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvAllSongs.layoutManager = layoutManager
        binding.rvAllSongs.adapter = playlistAdapter


        subscribeToObservers()
    }


    private fun subscribeToObservers() {
        mainViewModel.mediaItems.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    binding.allSongsProgressBar.isVisible = false
                    result.data?.let { songs ->
                        playlistAdapter.songs = songs
                    }
                }
                Status.ERROR -> Unit
                Status.LOADING -> binding.allSongsProgressBar.isVisible = true
            }
        }
    }
}
