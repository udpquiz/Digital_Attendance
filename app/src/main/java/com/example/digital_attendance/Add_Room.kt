package com.example.digital_attendance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Add_Room : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_room)

        val ADD_R_CODE: EditText =findViewById(R.id.add_r_id)
        val ADD_R_NAME: EditText =findViewById(R.id.add_r_name)

        val ADD_ROOM: Button =findViewById(R.id.btn_add_room)

        val sql = LJCRUD1(this)

        ADD_ROOM.setOnClickListener {
            val r:Boolean = sql.addroom(ADD_R_CODE.text.toString(),ADD_R_NAME.text.toString())
            if (r == true)
                Toast.makeText(this,"Room Added!!", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this,"Room Insertion Failed!!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,Admin_Dashboard::class.java))
        }
    }
}