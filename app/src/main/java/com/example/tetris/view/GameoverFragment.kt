package com.example.tetris.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.tetris.R
import com.example.tetris.databinding.GameoverBinding
import com.example.tetris.viewmodel.TetrisViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class GameoverFragment : Fragment() {
  private val viewModel: TetrisViewModel by sharedViewModel()
  private lateinit var binding: GameoverBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = DataBindingUtil.inflate(inflater, R.layout.gameover, container, false)
    binding.viewModel = viewModel
    binding.lifecycleOwner = this
    return binding.root
  }
}