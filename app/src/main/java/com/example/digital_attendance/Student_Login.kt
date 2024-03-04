package com.example.digital_attendance
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.example.digital_attendance.Student_Page
import com.opencsv.CSVReader
import java.io.InputStreamReader

class Student_Login : AppCompatActivity() {
    lateinit var c: Cursor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_login)

        val inputEnroll = findViewById<EditText>(R.id.inputEnroll)
        val inputPassword = findViewById<EditText>(R.id.inputPassword)
        val btn = findViewById<Button>(R.id.login_BTtn)
        val mp = datahelper(this)

        val inputStream = assets.open("data.csv")

        inputStream.use {
            val csvReader = CSVReader(InputStreamReader(it))
            var nextLine: Array<String>?

            while (csvReader.readNext().also { nextLine = it } != null) {
                val isInserted: Boolean = mp.insertData(
                    nextLine?.get(0) ?: "", nextLine?.get(1) ?: ""
                )
            }
            csvReader.close()
        }

        btn.setOnClickListener {
            val b = Bundle()
            c = mp.getAllData() ?: return@setOnClickListener

            // Check for credentials
            if (inputEnroll.text.toString() == "admin@gmail.com" && inputPassword.text.toString() == "123456") {
                startActivity(Intent(this, Admin_Page::class.java))
            } else {
                if (c.moveToFirst()) {
                    do {
                        val enrollment = c.getString(0)
                        println(enrollment)

                        if (inputEnroll.text.toString() == c.getString(0) && inputPassword.text.toString() == c.getString(
                                1
                            )
                        ) {
                            // Successful login
                            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                            b.putString("enrollment", enrollment)
                            val i = Intent(this, Student_Page::class.java)
                            i.putExtras(b)
                            startActivity(i)

                            return@setOnClickListener // Exit the onClick listener
                        }
                    } while (c.moveToNext())
                    Toast.makeText(
                        this,
                        "Invalid credentials",
                        Toast.LENGTH_SHORT
                    ).show()

                    c.close()
                }
            }
        }
    }
}
