package com.example.digital_attendance

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.digital_attendance.com.example.digital_attendance.Faculty
import com.example.digital_attendance.com.example.digital_attendance.FacultyAdapter

class View_Faculty : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FacultyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_faculty)

        recyclerView = findViewById(R.id.recyclerView)
        // Assuming you have a list of Faculty objects named facultyList
        adapter = FacultyAdapter(this)
        recyclerView.adapter = adapter

        fetchDataAndSetToAdapter()

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchDataAndSetToAdapter() {
        val facultyList = getFacultyList()
        adapter.setData(facultyList)
        adapter.notifyDataSetChanged()
    }

    private fun getFacultyList(): List<Faculty> {
        val sql = LJCRUD1(this)
        val cursor = sql.viewFaculty() ?: return emptyList() // Null check added

        val facultyList = mutableListOf<Faculty>()

        try {
            if (cursor.moveToFirst()) {
                do {
                    val codeIndex = cursor.getColumnIndexOrThrow("f_code")
                    val nameIndex = cursor.getColumnIndexOrThrow("f_name")
                    val emailIndex = cursor.getColumnIndexOrThrow("f_email")
                    val passwordIndex = cursor.getColumnIndexOrThrow("f_password")

                    val code = cursor.getString(codeIndex)
                    val name = cursor.getString(nameIndex)
                    val email = cursor.getString(emailIndex)
                    val password = cursor.getString(passwordIndex)

                    facultyList.add(Faculty(code, name, email, password))
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor.close()
        }

        return facultyList
    }


}







//
//import android.database.Cursor
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.widget.ArrayAdapter
//import android.widget.ListView
//import android.widget.Toast
//import com.example.attendance.R
//import com.example.attendance.crudFaculty
//import com.example.exam_practise.sqlConnecion
//
//class View_Faculty : AppCompatActivity() {
//    lateinit var c: Cursor
//    lateinit var aa:ArrayAdapter<String>
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_view_faculty)

//        val LIST:ListView =findViewById(R.id.lv)
//
//        val sql = crudFaculty(this)
//
//        c = sql.viewFaculty()!!
//        if(c.getCount() === 0)
//        {
//            Toast.makeText(this,"Record Not Added!!", Toast.LENGTH_SHORT).show()
//        }
//
//        aa = ArrayAdapter(this,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
//        c.moveToFirst()
//        while (!c.isAfterLast)
//        {
//            aa.add(c.getString(0)+" "+c.getString(2))
//            c.moveToNext()
//        }
//        c.close()
//        LIST.adapter=aa
//    }
//}

