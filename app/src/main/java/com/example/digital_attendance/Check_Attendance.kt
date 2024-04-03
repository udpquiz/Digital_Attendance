package com.example.digital_attendance

import BackgroundWorker3
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

class Check_Attendance : AppCompatActivity() {
    lateinit var c: Cursor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_attendance)
        val b1 = intent.extras
        val enroll = b1?.getString("enrollment","??")
        Log.d("PrintedValue",enroll.toString())

        val spinner: Spinner = findViewById(R.id.spin_subject)
        val btn = findViewById<Button>(R.id.checkat)
        val percent = findViewById<TextView>(R.id.per)
        val present = findViewById<TextView>(R.id.tp)
        val absent = findViewById<TextView>(R.id.ta)

        val listView: ListView = findViewById(R.id.listView)
        val ab = arrayOf("rm","PLSQL","fc")
        val db = LJCRUD1(this)
        val sp = getSharedPreferences("student_details", MODE_PRIVATE)
        val editor = sp.edit()
        val sem = sp.getString("semester", "defaultt").toString()
        val div = sp.getString("division", "defaultt").toString()
        val subjectList = ArrayList<String>()
        c = db.Attendance_subject(sem.toInt())!!
        if(c==null){
            Log.d("cursor","Error")
        }
        while (c.moveToNext()) {
            val value = c.getString(2)
            subjectList.add(value)
        }
            val adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item,subjectList)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter

        btn.setOnClickListener {
            val selectedValue = spinner.selectedItem.toString()
            Log.d("Selected","$selectedValue")
            Log.d("Selected","${div}${sem}_${selectedValue}")
            Log.d("Selected","$enroll")
            val table = "${div}${sem}_${selectedValue}"
            val url = "http://192.168.2.84/studentAttendance.php"
            BackgroundWorker3(this){attendanceResult ->
                // Update UI with received data
//                Toast.makeText(this, "Total Count of 'P': ${attendanceResult.totalCountP}, Total Count of 'A': ${attendanceResult.totalCountA}", Toast.LENGTH_SHORT).show()
                percent.text = "${attendanceResult.percentage}"
                present.text = "${attendanceResult.totalCountP}"
                absent.text = "${attendanceResult.totalCountA}"
                val attendanceDataList = ArrayList<String>()

                // Access other variables from attendanceResult as needed
                for ((key, value) in attendanceResult.attendanceData) {
                    attendanceDataList.add("$key: $value")
                    Log.d("AttendanceData", "$key: $value")
                }
                val adapter = ArrayAdapter(this, R.layout.listview_item_attendance, attendanceDataList)
                listView.adapter = adapter
                Log.d("Percentage", "Percentage: ${attendanceResult.percentage}")
            }.execute(url, table, enroll)
        }
    }
}