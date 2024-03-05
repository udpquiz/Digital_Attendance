package com.example.digital_attendance

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class View_Schedule : AppCompatActivity() {
    lateinit var c: Cursor
    lateinit var aa: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_schedule)
        val list1: ListView =findViewById(R.id.list1)

        val sql = schedule_crud(this)

        c = sql.viewsc()!!
        if(c.getCount() === 0)
        {
            Toast.makeText(this,"Record Not Added!!", Toast.LENGTH_SHORT).show()
        }

        aa = ArrayAdapter(this,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
        c.moveToFirst()
        while (!c.isAfterLast)
        {
            aa.add(c.getString(0)+" "+c.getString(1)+" "+c.getString(2)+" "+c.getString(3)+" "+c.getString(4)+" "+c.getString(5)+" "+c.getString(6)+" "+c.getString(7))
            c.moveToNext()
        }
        c.close()
        list1.adapter=aa
    }
}