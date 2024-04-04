package com.example.digital_attendance

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.digital_attendance.com.example.digital_attendance.FacultyAdapter

class View_Faculty : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FacultyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_faculty)

        recyclerView = findViewById(R.id.f_recyclerView)
        // Assuming you have a list of Faculty objects named facultyList
        adapter = FacultyAdapter(this)
        recyclerView.adapter = adapter

        fetchDataAndSetToAdapter()

    }

    private fun fetchDataAndSetToAdapter() {
        val facultyList = getFacultyList()
        adapter.setData(facultyList)
    }

    private fun getFacultyList(): List<Faculty> {
        val sql = LJCRUD1(this)
        val cursor = sql.viewFaculty()!! //?: return emptyList() // Null check added

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

//    private fun getFacultyList(): List<Faculty> {
//        val sql = LJCRUD1(this)
//        val cursor = sql.viewFaculty()!! // Null check added
//
//        val facultyList = mutableListOf<Faculty>()
//
//        try {
//            if (cursor.moveToFirst()) {
//                do {
//                    val codeIndex = cursor.getColumnIndexOrThrow("f_code")
//                    val nameIndex = cursor.getColumnIndexOrThrow("f_name")
//                    val emailIndex = cursor.getColumnIndexOrThrow("f_email")
//                    val passwordIndex = cursor.getColumnIndexOrThrow("f_password")
//
//                    val code = cursor.getString(codeIndex)
//                    val name = cursor.getString(nameIndex)
//                    val email = cursor.getString(emailIndex)
//                    val password = cursor.getString(passwordIndex)
//
//                    facultyList.add(Faculty(code, name, email, password))
//                } while (cursor.moveToNext())
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        } finally {
//            cursor.close()
//        }
//
//        return facultyList
//    }




