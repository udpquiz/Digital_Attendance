package com.example.digital_attendance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Add_Course : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)

        val ADD_C_CODE: EditText =findViewById(R.id.add_c_id)
        val ADD_SEM: EditText =findViewById(R.id.add_c_sem)
        val ADD_NAME: EditText =findViewById(R.id.add_c_name)

        val ADD_COURSE: Button =findViewById(R.id.btn_add_course)

        val sql = Crud_Course(this)

        ADD_COURSE.setOnClickListener {
            val r:Boolean = sql.addCourse(ADD_C_CODE.text.toString(),ADD_SEM.text.toString(),ADD_NAME.text.toString())
            if (r == true)
                Toast.makeText(this,"Course Added!!", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this,"Course Insertion Failed!!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,Manage_Course::class.java))
        }
    }
}