package com.example.digital_attendance

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Admin_Login: AppCompatActivity() {

    private lateinit var c: Cursor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)

        val btn_login: Button = findViewById(R.id.Btn_faculty_login)
        btn_login.setOnClickListener {
            startActivity(Intent(this, Admin_Dashboard::class.java))
        }

        val sql = LJCRUD1(this)

        val uname: EditText = findViewById(R.id.input_admin_uname)
        val password: EditText = findViewById(R.id.input_password)



        val btn: Button = findViewById(R.id.Btn_faculty_login)
        btn.setOnClickListener {
            val b = Bundle()
            c = sql.viewFaculty()!!
            if (uname.text.toString() == "Admin@gmail.com" && password.text.toString() == "123456") {
                startActivity(Intent(this, Admin_Dashboard::class.java))
            } else {
                if (c.moveToFirst()) {

                    do {
                        val storedUsername = c.getString(0)
                        val storedname = c.getString(1)
                        val storedPassword = c.getString(3)
                        val email = c.getString(2)

                        b.putString("F_code", storedUsername)
                        b.putString("F_Name", storedname)
                        if (uname.text.toString() == storedUsername && password.text.toString() == storedPassword) {
                            // Successful login
                            Toast.makeText(this, "Successful", Toast.LENGTH_SHORT)
                                .show()
                            val i = Intent(this, Faculty_Page::class.java)
                            i.putExtras(b)
                            startActivity(i)

                            return@setOnClickListener
                        }

                    } while (c.moveToNext())
                    Toast.makeText(this, "Wrong Username & Password", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}