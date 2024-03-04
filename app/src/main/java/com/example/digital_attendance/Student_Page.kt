package com.example.digital_attendance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Student_Page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_page)
        val b = intent.extras
        val t = findViewById<TextView>(R.id.t)
        t.text = b?.getString("enrollment")
    }
}