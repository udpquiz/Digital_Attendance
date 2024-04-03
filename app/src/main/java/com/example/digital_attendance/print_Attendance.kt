    package com.example.digital_attendance
    import Student
    import android.content.Intent
    import android.os.Bundle
    import android.util.Log
    import android.widget.Button
    import android.widget.TextView
    import androidx.appcompat.app.AppCompatActivity

    class print_Attendance : AppCompatActivity() {
        lateinit var table:String
        lateinit var column_name:String

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_print_attendance)
            val mvf = findViewById<Button>(R.id.mvf)
            mvf.setOnClickListener {
                val intent = Intent(this,Faculty_Page::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        }
    }
