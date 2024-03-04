package com.example.digital_attendance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnloginAsStudent = findViewById<Button>(R.id.student_Login)
        btnloginAsStudent.setOnClickListener {
            startActivity(Intent(this,Student_Login::class.java))
        }
    }
}