package com.example.digital_attendance
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.digital_attendance.Schedule2

class Faculty_Page : AppCompatActivity() {
    lateinit var c: Cursor
    lateinit var c1: Cursor
    lateinit var storedname:String
    private lateinit var new2:String
    lateinit var aa:ArrayAdapter<String>
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: Schedule2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_page)
        val b = intent.extras
//        val listview = findViewById<ListView>(R.id.listView)
        val faculty_name = findViewById<TextView>(R.id.faculty_name)
        faculty_name.text = b?.getString("F_Name","Not Found")
//        Toast.makeText(this,"?",Toast.LENGTH_SHORT).show()
        val db = LJCRUD1(this)
        c = db.viewsc()!!
        c.moveToFirst()
        while(!c.isAfterLast){
            do{
                val storename = c.getString(7)
                if(storename==faculty_name.text) {
                     storedname = storename
                     new2 = storedname
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
        c1 = db. faculty_schedule(new2)!!
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
//        listview.adapter=aa
    }
}