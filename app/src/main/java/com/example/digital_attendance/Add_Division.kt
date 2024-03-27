package com.example.digital_attendance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Add_Division : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_division)
        val ADD_D_NAME: EditText = findViewById(R.id.add_d_name)

        val ADD_ROOM: Button = findViewById(R.id.btn_add_div)

        val sql = LJCRUD1(this)

        ADD_ROOM.setOnClickListener {
            val divisionName = ADD_D_NAME.text.toString()
            val divisionExists = sql.checkDivisionExists(divisionName)
            val r: Boolean = sql.adddivision(divisionName)
            if (divisionExists) {
                Toast.makeText(this, "Division already exists!", Toast.LENGTH_SHORT).show()
            } else {
                val isSuccess = sql.adddivision(divisionName)
                if (isSuccess) {
                    Toast.makeText(this, "Division added successfully!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, Admin_Dashboard::class.java))
                } else {
                    Toast.makeText(this, "Failed to add division!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}