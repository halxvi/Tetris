package com.example.tetris.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.tetris.R
import com.example.tetris.databinding.TetrisBinding
import com.example.tetris.model.TetrisViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TetrisFragment : Fragment() {
  private val viewModel: TetrisViewModel by sharedViewModel()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val binding: TetrisBinding =
      DataBindingUtil.inflate(inflater, R.layout.tetris, container, false)
    binding.lifecycleOwner = viewLifecycleOwner
    binding.viewModel = viewModel
    return binding.root
  }
}