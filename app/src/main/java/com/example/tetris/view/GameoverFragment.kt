package com.example.tetris.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tetris.databinding.GameoverBinding
import kotlinx.android.synthetic.main.gameover.*

class GameoverFragment : Fragment() {
  private var _binding: GameoverBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = GameoverBinding.inflate(inflater, container, false)
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

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}