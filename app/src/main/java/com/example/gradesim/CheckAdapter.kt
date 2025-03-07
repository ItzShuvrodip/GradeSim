package com.example.gradesim

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CheckAdapter(private val subjects: List<Subject>) : RecyclerView.Adapter<CheckAdapter.CheckViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.check_item, parent, false)
        return CheckViewHolder(view)
    }

    override fun onBindViewHolder(holder: CheckViewHolder, position: Int) {
        holder.bind(subjects[position])
    }

    override fun getItemCount(): Int = subjects.size

    class CheckViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val subjectName: TextView = itemView.findViewById(R.id.subjectName)
        private val creditPoints: TextView = itemView.findViewById(R.id.creditPoints)
        private val gradeDropdown: Spinner = itemView.findViewById(R.id.gradeDropdown)

        fun bind(subject: Subject) {
            subjectName.text = subject.name
            creditPoints.text = subject.credit.toString()

            val gradeOptions = arrayOf("A+", "A", "B+", "B", "C+", "C", "D")
            val adapter = ArrayAdapter(itemView.context, android.R.layout.simple_spinner_item, gradeOptions)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            gradeDropdown.adapter = adapter
            val position = gradeOptions.indexOf(subject.selectedGrade)
            if (position >= 0) gradeDropdown.setSelection(position)
            gradeDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                    subject.selectedGrade = gradeOptions[pos]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }
}