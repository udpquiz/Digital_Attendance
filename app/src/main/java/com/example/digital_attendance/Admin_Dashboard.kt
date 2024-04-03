package com.example.digital_attendance

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
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
        add_room.setOnClickListener{
            val i:Intent=Intent(this,Add_Room::class.java)
            startActivity(i)
        }
        val add_div:CardView=findViewById(R.id.add_div)
        add_div.setOnClickListener{
            val i:Intent=Intent(this,Add_Division::class.java)
            startActivity(i)
        }
        val back = findViewById<ImageView>(R.id.back)
        back.setOnClickListener{
            startActivity(Intent(this,Student_Login::class.java))
        }




    }
}