package com.example.digital_attendance

import android.app.DatePickerDialog
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import java.util.Calendar


class Student_Page : AppCompatActivity() {
    lateinit var c:Cursor
    lateinit var aa:ArrayAdapter<String>
    lateinit var tsem:TextView
    lateinit var tdiv:TextView
    private lateinit var date1: TextView
    private lateinit var calendar: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_page)
        val logout:Button=findViewById(R.id.logout)
        val list1:ListView=findViewById(R.id.list1)
        val sp=getSharedPreferences("student_details", MODE_PRIVATE)
        val editor=sp.edit()
        val b = intent.extras
        val t = findViewById<TextView>(R.id.t)
        tsem = findViewById<TextView>(R.id.tsem)
        tdiv = findViewById<TextView>(R.id.tdiv)
        date1 = findViewById(R.id.date1)
        calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val dayOfmonth = calendar.get(Calendar.DAY_OF_MONTH)
        val dayOfMonth = dayOfmonth + 1
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        date1.text = "$dayOfMonth/$month/$year"
        date1.setOnClickListener {
            showDatePickerDialog()
        }
        t.text = b?.getString("enrollment")
        t.setText(sp.getString("enrollment","defaultt"))
        tsem.setText(sp.getString("semester","defaultt"))
        tdiv.setText(sp.getString("division","defaultt"))
        val sem1=tsem.text.toString()
        val div1=tdiv.text.toString()
        val db=LJCRUD1(this)
        c= db.student_schedule(date1.text.toString(),sem1,div1)!!
        aa= ArrayAdapter(this,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
        c.moveToFirst()
        while (!c.isAfterLast()){
            aa.add("D:${c.getString(1)}" +
                    " ${c.getString(4)}\n" +
                    "To ${c.getString(5)}" +
                    " Sub:${c.getString(6)}\nFac:${c.getString(7)}\n" +
                    "Rom:${c.getString(8)}")
            c.moveToNext()
        }
        c.close()
        list1.adapter=aa
        logout.setOnClickListener {
            editor.clear()
            editor.apply()
            editor.commit()
            startActivity(Intent(this,Student_Login::class.java))
            Toast.makeText(this, "Logout Success", Toast.LENGTH_SHORT).show()
        }


    }
    fun updateDataOnDateChange(selectedDate: String) {
        val sem1 = tsem.text.toString()
        val div1 = tdiv.text.toString()
        val db = LJCRUD1(this)
        c = db.student_schedule(selectedDate, sem1, div1)!!
        aa.clear() // Clear previous data
        c.moveToFirst()
        while (!c.isAfterLast()) {
            aa.add("D:${c.getString(1)}" +
                    " ${c.getString(4)}\n" +
                    "To ${c.getString(5)}" +
                    " Sub:${c.getString(6)}\nFac:${c.getString(7)}\n" +
                    "Rom:${c.getString(8)}")
            c.moveToNext()
        }
        c.close()
        aa.notifyDataSetChanged() // Notify adapter that data has changed
    }

    fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            this,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                // Save the selected date
                val selectedDate = "$dayOfMonth/${month + 1}/$year"
                date1.text = selectedDate
                updateDataOnDateChange(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
// maaz
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()
    }
}