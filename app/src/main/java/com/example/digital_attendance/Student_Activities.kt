package com.example.digital_attendance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class Student_Activities : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val b = intent.extras
        val enrollment = b?.getString("enrollment")
        Log.d("Student Act",enrollment.toString())
        setContentView(R.layout.activity_student_activities)
        val sc = findViewById<Button>(R.id.schedulebtn)
        val at = findViewById<Button>(R.id.attendancebtn)
        val b1 = Bundle()
        b1.putString("enrollment",enrollment)
        sc.setOnClickListener {
            val intent = Intent(this,Student_Page::class.java)
            intent.putExtras(b1)
            startActivity(intent)
        }
        at.setOnClickListener {
            val intent = Intent(this,Check_Attendance::class.java)
            intent.putExtras(b1)
            println(b1)
            startActivity(intent)
        }
    }
}