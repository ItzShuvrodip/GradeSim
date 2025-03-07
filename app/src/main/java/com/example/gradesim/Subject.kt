package com.example.gradesim

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Subject(
    var name: String,
    var credit: Float,
    var selectedGrade: String = ""
) : Parcelable {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "name" to name,
            "credit" to credit,
            "grade" to selectedGrade
        )
    }
}
