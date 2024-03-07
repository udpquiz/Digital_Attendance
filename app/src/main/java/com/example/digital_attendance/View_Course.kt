package com.example.digital_attendance

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class View_Course : AppCompatActivity() {
    lateinit var c: Cursor
    lateinit var aa: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_course)
        val listview: ListView =findViewById(R.id.listView)

        val sql = LJCRUD1(this)

        c = sql.viewCourse()!!
        if(c.getCount() === 0)
        {
            Toast.makeText(this,"Record Not Added!!", Toast.LENGTH_SHORT).show()
        }

        aa = ArrayAdapter(this,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
        c.moveToFirst()
        while (!c.isAfterLast)
        {
            aa.add(c.getString(0)+"   "+c.getString(1)+"   "+c.getString(2))
            c.moveToNext()
        }
        c.close()
        listview.adapter=aa
    }
}