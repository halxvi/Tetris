package com.example.tetris.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tetris.R
import com.example.tetris.misc.RankingViewAdapter
import com.example.tetris.misc.Score
import com.example.tetris.model.Repository
import kotlinx.android.synthetic.main.ranking.*

class RankingFragment : Fragment() {
  private val repository = Repository()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.ranking, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    backButton.setOnClickListener{
      val rankingFragment = parentFragmentManager.findFragmentByTag("Ranking")
      val gameoverFragment = parentFragmentManager.findFragmentByTag("Gameover")
      if (rankingFragment != null && gameoverFragment != null) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.detach(rankingFragment)
        transaction.attach(gameoverFragment)
        transaction.commit()
      }
    }
    goToSubmitFormButton.setOnClickListener {
      val rankingFragment = parentFragmentManager.findFragmentByTag("Ranking")
      val rankingSubmitFormFragment = parentFragmentManager.findFragmentByTag("RankingSubmitForm")
      if (rankingFragment != null && rankingSubmitFormFragment != null) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.detach(rankingFragment)
        transaction.attach(rankingSubmitFormFragment)
        transaction.commit()
      }
    }

    val dataSet = repository.fetchScore()?.value
    rankingRecyclerView.adapter = RankingViewAdapter(dataSet as Array<Score>)
  }
}