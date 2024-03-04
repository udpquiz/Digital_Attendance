package com.example.digital_attendance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Admin_Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)

        val btn_login: Button =findViewById(R.id.Btn_faculty_login)
        btn_login.setOnClickListener {
            val i: Intent = Intent(this,Manage_Faculty::class.java)
            startActivity(i)
        }
    }
}