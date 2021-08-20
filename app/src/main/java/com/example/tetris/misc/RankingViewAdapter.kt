package com.example.tetris.misc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tetris.R

class RankingViewAdapter(private val dataSet: Array<Score>): RecyclerView.Adapter<RankingViewAdapter.ViewHolder>()  {
  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val rankingScoreTextView: TextView = view.findViewById(R.id.rankingScoreTextView)
    val rankingNameTextView:TextView = view.findViewById(R.id.rankingNameTextView)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context)
      .inflate(R.layout.ranking_list, parent, false)

    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.rankingScoreTextView.text = dataSet[position].score.toString()
    holder.rankingNameTextView.text = dataSet[position].user_name.toString()
  }

  override fun getItemCount(): Int {
    return dataSet.size
  }
}