package com.example.digital_attendance

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView

class Admin_Dashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        val b=intent.extras

//        val heading: TextView =findViewById(R.id.heading)

        val btn_Add_Faculty:CardView=findViewById(R.id.add_faculty)
        btn_Add_Faculty.setOnClickListener{
            val i:Intent=Intent(this,Add_Faculty::class.java)
            startActivity(i)
        }

        val btn_View_Faculty:CardView=findViewById(R.id.view_faculty)
        btn_View_Faculty.setOnClickListener{
            val i:Intent=Intent(this,View_Faculty::class.java)
            startActivity(i)
        }

        val btn_Add_Course:CardView=findViewById(R.id.add_course)
        btn_Add_Course.setOnClickListener{
            val i:Intent=Intent(this,Add_Course::class.java)
            startActivity(i)
        }

        val btn_Add_Schedule:CardView=findViewById(R.id.add_schedule)
        btn_Add_Schedule.setOnClickListener{
            val i:Intent=Intent(this,Add_Schedule::class.java)
            startActivity(i)
        }

        val btn_View_Schedule:CardView=findViewById(R.id.view_schedule)
        btn_View_Schedule.setOnClickListener{
            val i:Intent=Intent(this,View_Schedule::class.java)
            startActivity(i)
        }

        val btn_View_Course:CardView=findViewById(R.id.view_course)
        btn_View_Course.setOnClickListener{
            val i:Intent=Intent(this,View_Course::class.java)
            startActivity(i)
        }
        val add_room:CardView=findViewById(R.id.add_room)
//        add_room.setOnClickListener{
//            val i:Intent=Intent(this,Add_Room::class.java)
//            startActivity(i)
//        }
        val add_div:CardView=findViewById(R.id.add_div)
//        add_div.setOnClickListener{
//            val i:Intent=Intent(this,Add_Division::class.java)
//            startActivity(i)
//        }
        val back = findViewById<ImageView>(R.id.back)
        back.setOnClickListener{
            startActivity(Intent(this,Admin_Login::class.java))
        }

        val sql = LJCRUD1(this)
        add_room.setOnClickListener {
            showAddRoomDialog(sql)
        }

        add_div.setOnClickListener {
            showAddDivisionDialog(sql)
        }

    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,MainActivity::class.java))
    }

    private fun showAddRoomDialog(sql: LJCRUD1) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add Room")

        val input = EditText(this)
        input.hint = "Enter Room Number"
        builder.setView(input)

        builder.setPositiveButton("Add") { dialog, which ->
            val roomNumber = input.text.toString()
            val roomExists = sql.checkRoomExists(roomNumber)
            if (roomExists) {
                Toast.makeText(this, "Room already exists!", Toast.LENGTH_SHORT).show()
            } else {
                val isSuccess = sql.addroom(roomNumber)
                if (isSuccess) {
                    Toast.makeText(this, "Room added successfully!", Toast.LENGTH_SHORT).show()
//                    startActivity(Intent(this, Admin_Dashboard::class.java))
                } else {
                    Toast.makeText(this, "Failed to add room!", Toast.LENGTH_SHORT).show()
                }
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun showAddDivisionDialog(sql: LJCRUD1) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add Division")

        val input = EditText(this)
        input.hint = "Ex 'ICA_A' "
        builder.setView(input)

        builder.setPositiveButton("Add") { dialog, which ->
            val divisionName = input.text.toString()
            val divisionExists = sql.checkDivisionExists(divisionName)
            if (divisionExists) {
                Toast.makeText(this, "Division already exists!", Toast.LENGTH_SHORT).show()
            } else {
                val isSuccess = sql.adddivision(divisionName)
                if (isSuccess) {
                    Toast.makeText(this, "Division added successfully!", Toast.LENGTH_SHORT).show()
//                    startActivity(Intent(this, Admin_Dashboard::class.java))
                } else {
                    Toast.makeText(this, "Failed to add division!", Toast.LENGTH_SHORT).show()
                }
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.cancel()
        }

        builder.show()
    }
}