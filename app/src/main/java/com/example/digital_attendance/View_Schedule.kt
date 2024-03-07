package com.example.digital_attendance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class View_Schedule : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ScheduleAdapter
    private lateinit var did:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_schedule)
        recyclerView = findViewById(R.id.r)
        adapter = ScheduleAdapter()
        val db=LJCRUD1(this)
        val sql = LJCRUD1(this)
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
                    c.getString(7),
                )
                did=c.getString(0)
//                deleteSchedule(did)
                schedules.add(schedule)
                c.moveToNext()
            }
            adapter.setData(schedules)
        } else {
            // Handle case when no records found
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
//    fun deleteSchedule(schduleId:String){
//        val db = LJCRUD(this)
//        val deletedRows = db.deleteSchedule(did)
//        if (deletedRows!! > 0) Toast.makeText(
//            applicationContext,
//            "Data Deleted",
//            Toast.LENGTH_LONG
//        ).show() else Toast.makeText(
//            applicationContext, "Data not Deleted", Toast.LENGTH_LONG
//        ).show()
//    }
}
