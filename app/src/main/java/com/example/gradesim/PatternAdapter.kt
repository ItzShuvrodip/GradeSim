package com.example.gradesim

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class PatternAdapter(
    private val patterns: List<Pattern>,
    private val onPatternClick: (Pattern) -> Unit  // Click listener
) : RecyclerView.Adapter<PatternAdapter.PatternViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatternViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pattern_item, parent, false)
        return PatternViewHolder(view)
    }

    override fun onBindViewHolder(holder: PatternViewHolder, position: Int) {
        holder.bind(patterns[position], onPatternClick)
    }

    override fun getItemCount(): Int = patterns.size

    class PatternViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val patternTitle: TextView = itemView.findViewById(R.id.patternTitle)
        private val subjectList: TextView = itemView.findViewById(R.id.subjectList)

        fun bind(pattern: Pattern, onPatternClick: (Pattern) -> Unit) {
            patternTitle.text = pattern.name
            subjectList.text = pattern.subjects.joinToString { "${it.name} (${it.credit} credits)" }

            itemView.setOnClickListener {
                onPatternClick(pattern)
            }
        }
    }
}
