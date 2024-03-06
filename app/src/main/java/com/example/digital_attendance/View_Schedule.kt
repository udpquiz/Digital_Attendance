package com.example.digital_attendance

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class View_Schedule : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ScheduleAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_schedule)
        recyclerView = findViewById(R.id.r)
        adapter = ScheduleAdapter()

        val sql = LJCRUD(this)
        val c = sql.viewsc()

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
                    c.getString(7)
                )
                schedules.add(schedule)
                c.moveToNext()
            }
            adapter.setData(schedules)
        } else {
            // Handle case when no records found
        }
        c?.close()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}