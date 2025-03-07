package com.example.gradesim

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PatternAdapter
    private lateinit var patternsList: MutableList<Pattern>
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_main)

        db = FirebaseFirestore.getInstance()
        recyclerView = findViewById(R.id.recyclerViewPatterns)
        patternsList = mutableListOf()

        adapter = PatternAdapter(patternsList) { selectedPattern ->
            openCheckPage(selectedPattern)
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchPatternsFromFirebase()

        val addPageButton: Button = findViewById(R.id.addPageButton)
        addPageButton.setOnClickListener {
            startActivity(Intent(this, AddPageActivity::class.java))
            finish()
        }
    }

    private fun fetchPatternsFromFirebase() {
        db.collection("patterns").get().addOnSuccessListener { documents ->
            patternsList.clear()
            for (document in documents) {
                val name = document.getString("name") ?: "Unknown Pattern"
                val subjects = (document["subjects"] as? List<Map<String, Any>>)?.map {
                    Subject(
                        it["name"] as String,
                        (it["credit"] as Number).toFloat(),
                        it["grade"] as String
                    )
                } ?: emptyList()

                patternsList.add(Pattern(name, subjects))
            }
            adapter.notifyDataSetChanged()
        }
    }

    private fun openCheckPage(pattern: Pattern) {
        val intent = Intent(this, CheckPageActivity::class.java)
        intent.putExtra("pattern_name", pattern.name)
        intent.putParcelableArrayListExtra("subjects", ArrayList(pattern.subjects))
        startActivity(intent)
    }
}
