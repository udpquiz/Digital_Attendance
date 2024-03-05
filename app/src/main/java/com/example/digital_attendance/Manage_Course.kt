package com.example.digital_attendance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Manage_Course : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_course)
        val btn_add_course: Button =findViewById(R.id.btn_a_course)
        btn_add_course.setOnClickListener {
            val i: Intent = Intent(this,Add_Course::class.java)
            startActivity(i)
        }
        val btn_view_course: Button =findViewById(R.id.btn_v_course)
        btn_view_course.setOnClickListener {
            val i: Intent = Intent(this,View_Course::class.java)
            startActivity(i)
        }
    }
}