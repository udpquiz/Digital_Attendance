package com.example.digital_attendance

import BackgroundWorker
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.opencsv.CSVReader
import java.io.InputStreamReader

class phpUplaod : AppCompatActivity() {
    private val tasksQueue = mutableListOf<BackgroundWorker>()
    private var currentIndex = 0
    private var division: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_php_uplaod)
        val b = intent.extras
        val a = b?.getString("table")
        division = b?.getString("division") // Retrieve division from intent extras
        Log.e("table","$a")
        try {
            val inputStream = assets.open("${division.toString()}.csv")
            val csvReader = CSVReader(InputStreamReader(inputStream))
            var nextLine: Array<String>?
            while (csvReader.readNext().also { nextLine = it } != null) {
                val enrollmentNumber = nextLine?.get(0) ?: ""
                val name = nextLine?.get(1) ?: ""
                val url = "http://192.168.248.207/insertData.php"
                // Pass a lambda function as onCompleteListener to the BackgroundWorker constructor
                val backgroundWorker = BackgroundWorker(this) {
                    executeNextTask()
                }
                tasksQueue.add(backgroundWorker)
                backgroundWorker.execute(url, a, enrollmentNumber, name)
            }
            csvReader.close()
            Toast.makeText(this, "Data Upload Started", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun executeNextTask() {
        currentIndex++
        if (currentIndex == tasksQueue.size) {
            startNextActivity()
        }
    }

    private fun startNextActivity() {
        val intent = Intent(this, Attendance_Recording::class.java)
        // Put division into the intent bundle
        division?.let { intent.putExtra("division", it) }
        startActivity(intent)
    }
}