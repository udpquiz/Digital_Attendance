package com.example.digital_attendance
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.opencsv.CSVReader
import java.io.InputStreamReader

class Student_Login : AppCompatActivity() {
    private val sharedfile = "student_details"
    lateinit var c: Cursor
    lateinit var c3: Cursor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_login)

        val inputEnroll = findViewById<EditText>(R.id.inputEnroll)
        val inputPassword = findViewById<EditText>(R.id.inputPassword)
        val btn = findViewById<Button>(R.id.login_BTtn)
        val mp = LJCRUD1(this)
//        val sp=getSharedPreferences("student_details", MODE_PRIVATE)
        val sp: SharedPreferences =this.getSharedPreferences(sharedfile, Context.MODE_PRIVATE)
        val editor = sp.edit()
        val sem: Spinner =findViewById(R.id.spin_sem)
        val div: Spinner =findViewById(R.id.spin_div)
        val semester= arrayOf("1","2","3","4","5","6")
        val aclass = ArrayList<String>()
        c3 = mp.viewdiv()!!
        while (c3.moveToNext()) {
            val value = c3.getString(1)
            aclass.add(value)
        }

        val ss: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.select_dialog_item, semester)
        sem.setAdapter(ss)
        val dd: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.select_dialog_item,aclass)
        div.setAdapter(dd)


        val inputStream = assets.open("Login.csv")

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
            c = mp.getAllData()!!

            // Check for credentials
            if (inputEnroll.text.toString() == "admin@gmail.com" && inputPassword.text.toString() == "123456") {
                startActivity(Intent(this, Admin_Dashboard::class.java))
            } else {
                if (c.moveToFirst()) {
                    do {
                        val enrollment = c.getString(0)
                        println(enrollment)

                        if (inputEnroll.text.toString() == c.getString(0) && inputPassword.text.toString() == c.getString(1)) {
                            // Successful login
                            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                            b.putString("enrollment", enrollment)
//                            if (!sp.contains("semester")) {
//                                val j = Intent(this, Student_Detail::class.java)
//                                j.putExtras(b)
//                                startActivity(j)
                                val selectedsem = sem.selectedItem.toString()
                                val selecteddiv = div.selectedItem.toString()
                                editor.putString("enrollment",enrollment)
                                editor.putString("semester",selectedsem)
                                editor.putString("division",selecteddiv)
                                editor.apply()
                                editor.commit()
//                                Toast.makeText(this,"Data Save",Toast.LENGTH_SHORT).show()
                            val i = Intent(this, Student_Activities::class.java)
                                i.putExtras(b)
                                startActivity(i)
//                            }
//                            else{
//                                editor.putString("enrollment",enrollment)
//                                editor.apply()
//                                editor.commit()
//                                val i = Intent(this, Student_Activities::class.java)
//                                i.putExtras(b)
//                                startActivity(i)
//                            }


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
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }
}
