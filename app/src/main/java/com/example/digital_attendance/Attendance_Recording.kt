package com.example.digital_attendance
import Student
import StudentAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.opencsv.CSVReader
import java.io.InputStreamReader
import java.util.Calendar
class Attendance_Recording : AppCompatActivity() {
    lateinit var table: String
    lateinit var lecture: String
    lateinit var new_column: String
    lateinit var menuid: String
    lateinit var calendar: Calendar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendance_recording)
        val studentattendance = findViewById<Button>(R.id.submitattendance)
        val studentRecyclerView: RecyclerView = findViewById(R.id.studentRecyclerView)
        val division = intent.getStringExtra("division")
        table = intent.getStringExtra("table").toString()
        lecture = intent.getStringExtra("lecture").toString()
        new_column = intent.getStringExtra("new_column").toString()
        menuid = intent.getStringExtra("menuId").toString()
        println(table)
        println("Lecture + $lecture")
        table = table.replace(" ", "_")
        new_column = new_column.replace(" ", "_")
        new_column = new_column.replace("/", "_")
        Log.d("Table name", table)
        Log.d("Table name", menuid)
        val inputStream = assets.open("${division}.csv")
        val reader = CSVReader(InputStreamReader(inputStream))
        val studentList = java.util.ArrayList<Student>()

        // Read CSV content and populate the studentList
        var nextLine: Array<String>?
        while (reader.readNext().also { nextLine = it } != null) {
            // Assuming your CSV file has columns like "Name", "Roll Number", etc.
            val name = nextLine!![0]
            val rollNumber = nextLine!![1]
            val student = Student(name, rollNumber, isPresent = false)
            studentList.add(student)
        }
        // Close the CSV reader
        reader.close()
        val studentAdapter = StudentAdapter(studentList)
        studentRecyclerView.layoutManager = LinearLayoutManager(this)
        studentRecyclerView.adapter = studentAdapter
        if(menuid=="2"){
            studentattendance.performClick()
            Toast.makeText(this, "Attendance Submitted Successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, print_Attendance::class.java)
        }
            studentattendance.setOnClickListener {
                val selectedStudents = studentAdapter.getSelectedStudents()
                val selectedEnrollments = selectedStudents.map { it.name }.joinToString(",")
                Log.d(
                    "SelectedStudents",
                    "Selected students: ${selectedStudents.joinToString { it.name + it.rollNumber + "\n" }} "
                )
                if (table.isEmpty()) {
                    Log.e("Error", "Table parameter is empty")
                    return@setOnClickListener
                }
                calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH) + 1
                val dayOfmonth = calendar.get(Calendar.DAY_OF_MONTH)
                val dayOfMonth = dayOfmonth + 1
                calendar.add(Calendar.DAY_OF_MONTH, 1)
                val date1 = "$dayOfMonth/$month$year"
                Log.d("Dateeee", "$date1")
                val url = "http://192.168.61.243/updateAttendance.php"
                val new_column1 = new_column
                Log.d("new_column1", new_column1)
                val unselectedEnrollments =
                    studentList.filter { !it.isPresent }.map { it.name }.joinToString(",")
                Log.d("Unselected", "$unselectedEnrollments")
                BackgroundWorker1(this) {
                }.execute(
                    url,
                    table,
                    selectedEnrollments,
                    new_column1,
                    "P",
                    unselectedEnrollments,
                    menuid
                )
                Log.d("URL", url)
                Log.d("table", table)
                Log.d("enrollment", selectedStudents.map { it.name }.joinToString())
                Log.d("new_column", new_column1)
                Toast.makeText(this, "Attendance Submitted Successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, print_Attendance::class.java)
                intent.putParcelableArrayListExtra("Students", ArrayList(selectedStudents))
                Log.d("AB", "$selectedEnrollments")
                startActivity(intent)
            }
        }
    }