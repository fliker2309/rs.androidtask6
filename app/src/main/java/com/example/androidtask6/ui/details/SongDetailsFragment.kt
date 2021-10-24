package com.example.androidtask6.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.androidtask6.R
import com.example.androidtask6.databinding.FragmentSongBinding
import com.example.androidtask6.presentation.MainViewModel

class SongDetailsFragment : Fragment(R.layout.fragment_song) {

    private var _binding: FragmentSongBinding? = null
    private val binding: FragmentSongBinding
        get() = requireNotNull(_binding)

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSongBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
