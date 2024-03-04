package com.example.digital_attendance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Manage_Faculty : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_faculty)

        val btn_m_add: Button =findViewById(R.id.btn_m_add_faculty)
        btn_m_add.setOnClickListener {
            val i= Intent(this,Add_Faculty::class.java)
            startActivity(i)
        }

        val btn_m_view:Button=findViewById(R.id.btn_m_view_faculty)
        btn_m_view.setOnClickListener {
            val i=Intent(this,View_Faculty::class.java)
            startActivity(i)
        }


        //
//        val btn_m_update:Button=findViewById(R.id.btn_m_update_faculty)
//        btn_m_update.setOnClickListener {
//            val i=Intent(this,Update_Faculty::class.java)
//            startActivity(i)
//        }
//
//        val btn_m_delete:Button=findViewById(R.id.btn_m_delete_faculty)
//        btn_m_delete.setOnClickListener {
//            val i=Intent(this,Delete_Faculty::class.java)
//            startActivity(i)
//        }
    }
}