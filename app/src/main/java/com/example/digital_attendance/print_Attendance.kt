package com.example.digital_attendance
import Student
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class print_Attendance : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_print_attendance)

        val studentsArrayList = intent.getSerializableExtra("students") as? ArrayList<Student>
        val students = studentsArrayList?.toTypedArray()

        displayAttendanceStatus(students)
    }
    private fun displayAttendanceStatus(students: Array<Student>?) {
        val attendanceTextView: TextView = findViewById(R.id.attendanceTextView)
        val attendanceStringBuilder = StringBuilder()
        students?.let {
            for (student in it) {
                val attendanceStatus = if (student.isPresent) "P" else "A"
                attendanceStringBuilder.append("${student.name}  $attendanceStatus\n")
            }
        }
        attendanceTextView.text = attendanceStringBuilder.toString()
    }
}