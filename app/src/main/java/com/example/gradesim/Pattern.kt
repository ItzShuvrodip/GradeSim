package com.example.gradesim

data class Pattern(val name: String, val subjects: List<Subject>) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "name" to name,
            "subjects" to subjects.map { it.toMap() }
        )
    }
}
