package com.example.digital_attendance

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class View_Faculty : AppCompatActivity() {
    lateinit var c: Cursor
    lateinit var aa: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_faculty)

        val LIST: ListView =findViewById(R.id.lv_f)

        val sql = LJCRUD1(this)

        c = sql.viewFaculty()!!
        if(c.getCount() === 0)
        {
            Toast.makeText(this,"Record Not Added!!", Toast.LENGTH_SHORT).show()
        }

        aa =  ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line)
        c.moveToFirst()
//        val headerView = layoutInflater.inflate(R.layout.list_header_layout, null)
//        LIST.addHeaderView(headerView)
        while (!c.isAfterLast)
        {
            aa.add(c.getString(0)+"   "+c.getString(1)+"   "+c.getString(2)+"   "+c.getString(3))
            c.moveToNext()
        }
        c.close()
        LIST.adapter=aa
    }
}