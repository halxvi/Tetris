package com.example.tetris.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tetris.R
import com.example.tetris.model.Repository
import kotlinx.android.synthetic.main.ranking_submit_form.*

class RankingSubmitFormFragment : Fragment() {
  private val repository = Repository()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.ranking_submit_form, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    submitButton.setOnClickListener {
      val text = editTextTextPersonName.text.toString()
      repository.write(text)
    }
  }
}