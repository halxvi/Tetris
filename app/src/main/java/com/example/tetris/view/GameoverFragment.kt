package com.example.tetris.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tetris.databinding.GameoverBinding
import com.example.tetris.viewmodel.TetrisViewModel
import com.example.tetris.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.gameover.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class GameoverFragment : Fragment() {
  private val userViewModel: UserViewModel by sharedViewModel()
  private val tetrisViewModel: TetrisViewModel by sharedViewModel()
  private var _binding: GameoverBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = GameoverBinding.inflate(inflater, container, false)
    binding.viewModel = tetrisViewModel
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    rankingButton.setOnClickListener{
      val transaction = parentFragmentManager.beginTransaction()

      if(userViewModel.isLogging){
        val rankingFragment = parentFragmentManager.findFragmentByTag("Ranking")
        val gameoverFragment = parentFragmentManager.findFragmentByTag("Gameover")
        if(rankingFragment != null && gameoverFragment != null) {
          transaction.attach(rankingFragment)
          transaction.detach(gameoverFragment)
        }
      } else {
        val gameoverFragment = parentFragmentManager.findFragmentByTag("Gameover")
        val authenticationFragment = parentFragmentManager.findFragmentByTag("Authentication")
        if(gameoverFragment != null && authenticationFragment != null) {
          transaction.attach(authenticationFragment)
          transaction.detach(gameoverFragment)
        }
      }

      transaction.commit()
    }

    restartButton.setOnClickListener {
     tetrisViewModel.restart()
     userViewModel.submittedScore.value = false
      val gameoverFragment = parentFragmentManager.findFragmentByTag("Gameover")
      if(gameoverFragment != null) {
        val transaction = parentFragmentManager.beginTransaction()
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