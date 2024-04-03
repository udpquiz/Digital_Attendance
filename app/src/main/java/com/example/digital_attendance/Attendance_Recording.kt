package com.example.digital_attendance
import BackgroundWorker1
import Student
import StudentAdapter
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.opencsv.CSVReader
import java.io.InputStreamReader
import java.util.Calendar

class Attendance_Recording : AppCompatActivity() {
    lateinit var table: String
    lateinit var lecture: String
    lateinit var sem: String
    lateinit var new_column: String
    lateinit var menuid: String
    lateinit var calendar: Calendar
    lateinit var studentAdapter: StudentAdapter
    lateinit var studentList: ArrayList<Student> // Define studentList as a class member

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
        sem = intent.getStringExtra("sem").toString()

        table = table.replace(" ", "_")
        new_column = new_column.replace(" ", "_")
        new_column = new_column.replace("/", "_")

        val inputStream = assets.open("${division}${sem}.csv")
        val reader = CSVReader(InputStreamReader(inputStream))
        studentList = ArrayList<Student>()

        var nextLine: Array<String>?
        while (reader.readNext().also { nextLine = it } != null) {
            val name = nextLine!![0]
            val rollNumber = nextLine!![1]
            val student = Student(name, rollNumber, isPresent = false)
            studentList.add(student)
        }
        reader.close()

        studentAdapter = StudentAdapter(studentList)
        studentRecyclerView.layoutManager = LinearLayoutManager(this)
        studentRecyclerView.adapter = studentAdapter

        studentattendance.setOnClickListener {
            val selectedStudents = studentAdapter.getSelectedStudents()
            val selectedEnrollments = selectedStudents.joinToString { it.name }
            showConfirmationDialog(selectedEnrollments)
            Log.d(
                "SelectedStudents",
                "Selected students: ${selectedStudents.joinToString { it.name + it.rollNumber + "\n" }} "
            )
        }
    }

    private fun showConfirmationDialog(selectedEnrollments: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirm Submission")
        builder.setMessage("Selected enrollment numbers:\n$selectedEnrollments")
        builder.setPositiveButton("OK") { dialog, which ->
            // User clicked OK, submit the data
            submitAttendance(selectedEnrollments) // Pass selectedEnrollments to submitAttendance()
        }
        builder.setNegativeButton("Cancel") { dialog, which ->
            // User cancelled the dialog, do nothing
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun submitAttendance(selectedEnrollments: String) {
        calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val dayOfmonth = calendar.get(Calendar.DAY_OF_MONTH)
        val dayOfMonth = dayOfmonth + 1
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        val date1 = "$dayOfMonth/$month$year"
        Log.d("Dateeee", "$date1")
        val url = "http://192.168.2.84/updateAttendance.php"
        val new_column1 = new_column
        Log.d("new_column1", new_column1)
        val unselectedEnrollments =
            studentList.filter { !it.isPresent }.joinToString { it.name } // Adjust this according to your requirements
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
        Toast.makeText(this, "Attendance Submitted Successfully", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, print_Attendance::class.java))
        finish()
    }
}
