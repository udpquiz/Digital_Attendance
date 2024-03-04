package com.example.digital_attendance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Faculty_Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faculty_login)
        val btn_login:Button=findViewById(R.id.Btn_faculty_login)
        btn_login.setOnClickListener {
            val i:Intent=Intent(this,Manage_Faculty::class.java)
            startActivity(i)
        }
    }
}