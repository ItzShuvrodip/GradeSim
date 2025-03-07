package com.example.gradesim

import android.app.Application
import com.google.firebase.FirebaseApp

class GradeSimApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}