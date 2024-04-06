package com.example.digital_attendance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnloginAsAdmin = findViewById<Button>(R.id.admin_login)
        btnloginAsAdmin.setOnClickListener {
            startActivity(Intent(this,Admin_Login::class.java))
        }

        val btnloginAsFaculty = findViewById<Button>(R.id.faculty_Login)
        btnloginAsFaculty.setOnClickListener {
            startActivity(Intent(this,Faculty_Login::class.java))
        }

        val btnloginAsStudent = findViewById<Button>(R.id.student_Login)
        btnloginAsStudent.setOnClickListener {
            startActivity(Intent(this,Student_Login::class.java))
        }
    }
}