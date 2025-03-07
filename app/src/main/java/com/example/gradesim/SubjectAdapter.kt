package com.example.gradesim

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView

class SubjectAdapter(
    private val subjects: MutableList<Subject>,
    private val updateTotalCredits: () -> Unit
) : RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder>() {

    class SubjectViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val subjectInput: EditText = view.findViewById(R.id.subjectInput)
        val creditInput: EditText = view.findViewById(R.id.creditInput)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.subject_item, parent, false)
        return SubjectViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        val subject = subjects[position]

        holder.subjectInput.setText(subject.name)
        holder.creditInput.setText(subject.credit.toString())

        holder.subjectInput.addTextChangedListener { text ->
            subjects[holder.adapterPosition].name = text.toString()
        }

        holder.creditInput.addTextChangedListener { text ->
            subjects[holder.adapterPosition].credit = text.toString().toFloatOrNull() ?: 0.0f
            updateTotalCredits()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position // Forces unique view type for each item
    }

    override fun getItemCount(): Int = subjects.size
}
