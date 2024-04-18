package com.example.digital_attendance

import android.app.DatePickerDialog
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Calendar

class Faculty_Page : AppCompatActivity() {
    lateinit var c: Cursor
    lateinit var c1: Cursor
    var storedname:String=""
    private lateinit var new2:String
    lateinit var aa:ArrayAdapter<String>
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: Schedule2
    private lateinit var date1: TextView
    private lateinit var calendar: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_page)
        val b = intent.extras
//        val listview = findViewById<ListView>(R.id.listView)
        val faculty_name = findViewById<TextView>(R.id.faculty_name)
        faculty_name.text = b?.getString("F_Name","Not Found")
//        Toast.makeText(this,"?",Toast.LENGTH_SHORT).show()
        date1 = findViewById(R.id.date1)
        calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val dayOfmonth = calendar.get(Calendar.DAY_OF_MONTH)
        val dayOfMonth = dayOfmonth + 1
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        date1.text = "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH)+1}/${calendar.get(Calendar.YEAR)}"
        date1.setOnClickListener {
            showDatePickerDialog()
        }
        val db = LJCRUD1(this)
        c = db.viewallsc()!!
        c.moveToFirst()
        while(!c.isAfterLast){
            do{
                val storename = c.getString(7)
                if(storename==faculty_name.text) {
                    storedname = storename.toString()
//                    new2 = storedname
                    println(storedname)
//                    Toast.makeText(this,"$storedname",Toast.LENGTH_SHORT).show()
                }
            }
            while(c.moveToNext())
        }
        c.close()
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = Schedule2()
        recyclerView.adapter = adapter
        c1 = db.faculty_schedule(storedname.toString(),date1.text.toString())!!
        if (c1 != null && c1.count > 0) {
            c1.moveToFirst()
            while (!c1.isAfterLast) {
                adapter.addSchedule(
                    "ID: ${c1.getString(0)}\n Date: ${c1.getString(1)}\n Sem: ${c1.getString(2)}" +
                            "\n Division: ${c1.getString(3)}\n Start Time: ${c1.getString(4)}\n" +
                            " End Time: ${c1.getString(5)} " +
                            "\n Subject: ${c1.getString(6)}\n Room No:  ${c1.getString(8)}\n Lec No: ${c1.getString(9)}"
                )
                c1.moveToNext()
            }
            c1.close()
        } else {
            Toast.makeText(this, "No Records Available", Toast.LENGTH_SHORT).show()
            // Handle case when no records found
        }
//        listview.adapter=aa
    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
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
    fun updateDataOnDateChange(selectedDate: String) {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = Schedule2()
        recyclerView.adapter = adapter
        val db = LJCRUD1(this)
        c1 = db.faculty_schedule(storedname.toString(),date1.text.toString())!!
        if (c1 != null && c1.count > 0) {
            c1.moveToFirst()
            while (!c1.isAfterLast) {
                adapter.addSchedule(
                    "ID: ${c1.getString(0)}\n Date: ${c1.getString(1)}\n Sem: ${c1.getString(2)} " +
                            "\n Division: ${c1.getString(3)}\n Start Time: ${c1.getString(4)}\n" +
                            " End Time: ${c1.getString(5)} " +
                            "\n Subject: ${c1.getString(6)}\n Room No:  ${c1.getString(8)}"
                )
                c1.moveToNext()
            }
            c1.close()
        } else {
            Toast.makeText(this, "No Records Available", Toast.LENGTH_SHORT).show()
            // Handle case when no records found
        }
    }
}