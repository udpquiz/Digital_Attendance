package com.example.digital_attendance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class Faculty_Page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_page)
        val b = intent.extras
        val faculty_id = findViewById<TextView>(R.id.faculty_id)
        faculty_id.text = "Logged In As "+b?.getString("F_code","Not Found")
//        Toast.makeText(this,"?",Toast.LENGTH_SHORT).show()
    }
}