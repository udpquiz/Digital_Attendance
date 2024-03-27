    package com.example.digital_attendance
    import Student
    import android.os.Bundle
    import android.util.Log
    import android.widget.TextView
    import androidx.appcompat.app.AppCompatActivity

    class print_Attendance : AppCompatActivity() {
        lateinit var table:String
        lateinit var column_name:String

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_print_attendance)
            val b = intent.extras
            val selectedEnrollment = b?.getString("Students")
            Log.d("SelectedEnrollments", selectedEnrollment ?: "No selected enrollments")
        }
    }
