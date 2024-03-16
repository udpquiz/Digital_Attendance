package com.example.digital_attendance

import Student
import StudentAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.opencsv.CSVReader
import java.io.InputStreamReader

class Attendance_Recording : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendance_recording)
        val studentattendance = findViewById<Button>(R.id.submitattendance)
        val studentRecyclerView: RecyclerView = findViewById(R.id.studentRecyclerView)
        val division = intent.getStringExtra("division")
        val inputStream = assets.open("${division}.csv")
        val reader = CSVReader(InputStreamReader(inputStream))
        val studentList = java.util.ArrayList<Student>()

        // Read CSV content and populate the studentList
        var nextLine: Array<String>?
        while (reader.readNext().also { nextLine = it } != null) {
            // Assuming your CSV file has columns like "Name", "Roll Number", etc.
            val name = nextLine!![0]
            val rollNumber = nextLine!![1]

            // Create a Student object and add it to the list
            val student = Student(name, rollNumber, isPresent = false)
            studentList.add(student)
        }
        // Close the CSV reader
        reader.close()
        val studentAdapter = StudentAdapter(studentList)

        studentRecyclerView.layoutManager = LinearLayoutManager(this)
        studentRecyclerView.adapter = studentAdapter
        studentattendance.setOnClickListener {
            val selectedStudents = studentAdapter.getSelectedStudents()
            Log.d("SelectedStudents", "Selected students: ${selectedStudents.joinToString { it.name + " " + it.rollNumber + "\n" }} ")

            val intent = Intent(this, print_Attendance::class.java)
            intent.putExtra("students", ArrayList(selectedStudents))
            startActivity(intent)
        }
    }
}