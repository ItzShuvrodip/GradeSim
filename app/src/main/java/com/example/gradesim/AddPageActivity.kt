package com.example.gradesim

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class AddPageActivity : AppCompatActivity() {
    private lateinit var subjectsList: MutableList<Subject>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SubjectAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var totalCreditText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addpage)

        db = FirebaseFirestore.getInstance()
        subjectsList = mutableListOf()

        recyclerView = findViewById(R.id.recyclerViewPatterns)
        totalCreditText = findViewById(R.id.totalCreditText) // Ensure this matches XML ID

        adapter = SubjectAdapter(subjectsList, ::updateTotalCredits) // Pass callback
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val addSubjectButton: Button = findViewById(R.id.addSubjectButton)
        val savePatternButton: Button = findViewById(R.id.savePatternButton)
        subjectsList.add(Subject("", 0.0f))
        adapter.notifyItemInserted(subjectsList.size - 1)

        addSubjectButton.setOnClickListener {
            subjectsList.add(Subject("", 0.0f))
            adapter.notifyItemInserted(subjectsList.size - 1)
            updateTotalCredits() // Update when a subject is added
        }

        savePatternButton.setOnClickListener {
            saveToFirebase(subjectsList)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun saveToFirebase(subjects: List<Subject>) {
        val name = findViewById<EditText>(R.id.patternNameInput).text.toString().trim()
        val validSubjects = subjects.filter { it.name.isNotEmpty() && it.credit > 0 }

        if (validSubjects.isNotEmpty() && name.isNotEmpty()) {
            val pattern = hashMapOf(
                "name" to name,
                "subjects" to validSubjects.map { it.toMap() }
            )
            db.collection("patterns").add(pattern)
        } else {
            Toast.makeText(this, "Please enter a pattern name and valid subjects", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateTotalCredits() {
        val totalCredits = subjectsList.sumOf { it.credit.toDouble() }
        totalCreditText.text = "Total Credits: %.1f".format(totalCredits)
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
        super.onBackPressed()
    }
}
