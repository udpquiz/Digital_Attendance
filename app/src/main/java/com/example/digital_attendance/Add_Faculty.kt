package com.example.digital_attendance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Add_Faculty : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_faculty)

        val btnadd: Button =findViewById(R.id.btn_add_faculty)

        val edfcode: EditText =findViewById(R.id.ed_f_code)
        val edfname: EditText =findViewById(R.id.ed_f_name)
        val edfemail: EditText =findViewById(R.id.ed_f_email)
        val edfpassword: EditText =findViewById(R.id.ed_f_password)


        val sql = LJCRUD1(this)


        btnadd.setOnClickListener {
            val r:Boolean = sql.insertFaculty(edfcode.text.toString().toInt(),
                edfname.text.toString(),edfemail.text.toString(),edfpassword.text.toString())
            if (r == true)
            {
                Toast.makeText(this, " Faculty added ",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,Admin_Dashboard::class.java))
            }
            else{
                Toast.makeText(this, "Faculty add failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, Admin_Dashboard::class.java))
    }
}