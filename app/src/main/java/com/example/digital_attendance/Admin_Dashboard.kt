package com.example.digital_attendance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Admin_Dashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        val b=intent.extras

        val heading: TextView =findViewById(R.id.heading)
        heading.text=b?.getString("email")
        val BtnMFaculty: Button =findViewById(R.id.btn_m_faculty)
        BtnMFaculty.setOnClickListener {
            val i: Intent = Intent(this, Manage_Faculty::class.java)
            startActivity(i)
        }

        val BtnMCourse: Button =findViewById(R.id.btn_m_course)
        BtnMCourse.setOnClickListener {
            val i: Intent = Intent(this,Manage_Course::class.java)
            startActivity(i)
        }

        val BtnMStudent: Button =findViewById(R.id.btn_m_student)
        BtnMStudent.setOnClickListener {
            val i: Intent = Intent(this,Add_Schedule::class.java)
            startActivity(i)
        }
    }
}