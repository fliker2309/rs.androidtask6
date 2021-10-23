package com.example.androidtask6.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding)

    lateinit var mainViewModel: MainViewModel


    lateinit var playlistAdapter: PlaylistAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        playlistAdapter = PlaylistAdapter(object : SongActionListener {
            override fun onSongClick(song: Song) {
                mainViewModel.playOrToggleSong(song)
                Toast.makeText(requireContext(), "Song ${song.title}", Toast.LENGTH_SHORT).show()
            }
        })
        setupRecyclerView()
        subscribeToObservers()
    }

    private fun setupRecyclerView() = binding.rvAllSongs.apply {
        adapter = playlistAdapter
        layoutManager = LinearLayoutManager(requireContext())
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
