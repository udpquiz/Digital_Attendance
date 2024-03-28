package com.example.digital_attendance

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Calendar

class View_Schedule : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ScheduleAdapter
    private lateinit var did:String
    private lateinit var date1: TextView
    private lateinit var calendar: Calendar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_schedule)
        recyclerView = findViewById(R.id.r)
        adapter = ScheduleAdapter(this)
        val db=LJCRUD1(this)
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
        val sql = LJCRUD1(this)
        val c = sql.viewsc(date1.text.toString())

        if (c != null && c.count > 0) {
            val schedules = mutableListOf<Schedule1>()
            c.moveToFirst()
            while (!c.isAfterLast) {
                val schedule = Schedule1(
                    c.getString(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3),
                    c.getString(4),
                    c.getString(5),
                    c.getString(6),
                    c.getString(7),
                    c.getString(8),
                    c.getString(9)

                )
//                deleteSchedule(did)
                schedules.add(schedule)
                c.moveToNext()
            }
            adapter.setData(schedules)
        } else {
            Toast.makeText(this, "No Records Available", Toast.LENGTH_SHORT).show()
            // Handle case when no records found
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter.setOnDeleteClickListener { scheduleId ->
            deleteSchedule(scheduleId)
    }
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
        val sql = LJCRUD1(this)
        val c = sql.viewsc(date1.text.toString())

        if (c != null && c.count > 0) {
            val schedules = mutableListOf<Schedule1>()
            c.moveToFirst()
            while (!c.isAfterLast) {
                val schedule = Schedule1(
                    c.getString(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3),
                    c.getString(4),
                    c.getString(5),
                    c.getString(6),
                    c.getString(7),
                    c.getString(8),
                    c.getString(9)

                )
//                deleteSchedule(did)
                schedules.add(schedule)
                c.moveToNext()
            }
            adapter.setData(schedules)
        } else {
            Toast.makeText(this, "No Records Available", Toast.LENGTH_SHORT).show()
            // Handle case when no records found
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter.setOnDeleteClickListener { scheduleId ->
            deleteSchedule(scheduleId)
        }

    }
    private fun deleteSchedule(scheduleId: String) {
        val db = LJCRUD1(this)
        val deletedRows = db.deleteSchedule(scheduleId)
        if (deletedRows!! > 0) {
            Toast.makeText(applicationContext, "Data Deleted", Toast.LENGTH_LONG).show()
            // Refresh the RecyclerView after deletion
            refreshRecyclerView()
        } else {
            Toast.makeText(applicationContext, "Data not Deleted", Toast.LENGTH_LONG).show()
        }
    }

    private fun refreshRecyclerView() {
        val c = LJCRUD1(this).viewsc(date1.text.toString())
        if (c != null && c.count > 0) {
            val schedules = mutableListOf<Schedule1>()
            c.moveToFirst()
            while (!c.isAfterLast) {
                val schedule = Schedule1(
                    c.getString(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3),
                    c.getString(4),
                    c.getString(5),
                    c.getString(6),
                    c.getString(7),
                    c.getString(8),
                    c.getString(9)
                )
                schedules.add(schedule)
                c.moveToNext()
            }
            adapter.setData(schedules)
        } else {
            adapter.setData(emptyList())
            // Handle case when no records found
        }
    }
}
