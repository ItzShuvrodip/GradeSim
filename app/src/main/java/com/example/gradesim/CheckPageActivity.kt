package com.example.gradesim

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CheckPageActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CheckAdapter
    private lateinit var subjectsList: MutableList<Subject>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.checkpage)

        val patternName = intent.getStringExtra("pattern_name") ?: "Pattern"
        subjectsList = intent.getParcelableArrayListExtra("subjects") ?: mutableListOf()

        findViewById<TextView>(R.id.patternTitle).text = patternName

        recyclerView = findViewById(R.id.recyclerViewSubjects)
        adapter = CheckAdapter(subjectsList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val calculateGPAButton: Button = findViewById(R.id.calculateGPAButton)
        calculateGPAButton.setOnClickListener {
            calculateGPA()
        }
    }

    private fun calculateGPA() {
        val grades = mapOf("A+" to 10, "A" to 9, "B+" to 8, "B" to 7, "C+" to 6, "C" to 5, "D" to 4)
        var totalPoints = 0.0f
        var totalCredits = 0.0f

        for (subject in subjectsList) {
            val selectedGrade = grades[subject.selectedGrade] ?: 0
            totalPoints += selectedGrade * subject.credit
            totalCredits += subject.credit
        }

        val gpa = if (totalCredits > 0) totalPoints.toDouble() / totalCredits else 0.0
        findViewById<TextView>(R.id.gpaResult).text = "GPA: %.2f".format(gpa)
    }
}
