package com.example.tetris.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tetris.databinding.GameoverBinding
import com.example.tetris.viewmodel.TetrisViewModel
import kotlinx.android.synthetic.main.gameover.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class GameoverFragment : Fragment() {
  private val viewModel: TetrisViewModel by sharedViewModel()
  private lateinit var binding: GameoverBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = GameoverBinding.inflate(inflater, container, false)
    binding.viewModel = viewModel
    binding.lifecycleOwner = this
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    rankingButton.setOnClickListener{
      val rankingFragment = parentFragmentManager.findFragmentByTag("Ranking")
      val gameoverFragment = parentFragmentManager.findFragmentByTag("Gameover")
      if(rankingFragment != null && gameoverFragment != null) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.attach(rankingFragment)
        transaction.detach(gameoverFragment)
        transaction.commit()
      }
    }
  }
}