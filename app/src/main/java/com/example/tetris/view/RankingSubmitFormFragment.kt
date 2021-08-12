package com.example.tetris.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tetris.databinding.RankingSubmitFormBinding
import com.example.tetris.model.Repository
import kotlinx.android.synthetic.main.ranking_submit_form.*

class RankingSubmitFormFragment : Fragment() {
  private val repository = Repository()
  private var _binding: RankingSubmitFormBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = RankingSubmitFormBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    submitButton.setOnClickListener {
      val text = editTextTextPersonName.text.toString()
      repository.write(text)
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}