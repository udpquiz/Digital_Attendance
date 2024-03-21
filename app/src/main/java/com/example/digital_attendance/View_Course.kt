package com.example.digital_attendance

import LJCRUD1
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class View_Course : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CourseAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_course)
        recyclerView = findViewById(R.id.c_recyclerView)
        // Assuming you have a list of Faculty objects named facultyList
        adapter = CourseAdapter(this)
        recyclerView.adapter = adapter

        fetchDataAndSetToAdapter()
    }

private fun fetchDataAndSetToAdapter() {
    val facultyList = getCourseList()
    adapter.setData(facultyList)
}
    private fun getCourseList(): List<Course> {
        val sql = LJCRUD1(this)
        val cursor = sql.viewCourse()!! // Null check added

        val courseList = mutableListOf<Course>()

        try {
            if (cursor.moveToFirst()) {
                do {
                    val codeIndex = cursor.getColumnIndexOrThrow("c_code")
                    val nameIndex = cursor.getColumnIndexOrThrow("NAME")
                    val semIndex = cursor.getColumnIndexOrThrow("SEM")

                    val code = cursor.getString(codeIndex)
                    val name = cursor.getString(nameIndex)
                    val sem = cursor.getString(semIndex)
                    courseList.add(Course(code, name, sem))
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor.close()
        }

        return courseList
    }
        //
//        val listview: ListView =findViewById(R.id.listView)
//
//        val sql = LJCRUD1(this)
//
//        c = sql.viewCourse()!!
//        if(c.getCount() === 0)
//        {
//            Toast.makeText(this,"Record Not Added!!", Toast.LENGTH_SHORT).show()
//        }
//
//        aa = ArrayAdapter(this,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
//        c.moveToFirst()
//        while (!c.isAfterLast)
//        {
//            aa.add(c.getString(0)+"   "+c.getString(1)+"   "+c.getString(2))
//            c.moveToNext()
//        }
//        c.close()
//        listview.adapter=aa

}