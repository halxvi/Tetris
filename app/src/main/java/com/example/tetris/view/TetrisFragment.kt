package com.example.tetris.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.tetris.R
import com.example.tetris.databinding.TetrisBinding
import com.example.tetris.viewmodel.TetrisViewModel
import kotlinx.android.synthetic.main.tetris.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TetrisFragment : Fragment() {
  private val viewModel: TetrisViewModel by sharedViewModel()
  private lateinit var binding: TetrisBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = TetrisBinding.inflate(inflater, container, false)
    binding.viewModel = viewModel
    binding.lifecycleOwner = this
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val fieldObserver = Observer<Array<Array<Int>>> { newField ->
      gameFieldView.setField(newField)
    }
    viewModel.fields.observe(viewLifecycleOwner, fieldObserver)
    val gameoverObserver = Observer<Boolean> { isGameover ->
      if(isGameover){
        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, GameoverFragment())
        transaction.commit()
      }
    }
    viewModel.gameover.observe(viewLifecycleOwner, gameoverObserver)
  }
}