package com.example.digital_attendance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.opencsv.CSVReader
import java.io.InputStreamReader
import java.util.Calendar

class phpUplaod : AppCompatActivity() {
    private val tasksQueue = mutableListOf<BackgroundWorker>()
    private var currentIndex = 0
    private var division: String? = null
    private var sem: String? = null
    private var lecture: String? = null
    lateinit var a:String
    lateinit var menuid : String
    lateinit var calendar:Calendar
    private var new_column: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_php_uplaod)
        val loadingProgressBar = findViewById<ProgressBar>(R.id.loadingProgressBar)
        loadingProgressBar.visibility = View.VISIBLE
        val b = intent.extras
        a = b?.getString("table").toString()
        menuid = b?.getInt("MenuId").toString()
        division = b?.getString("division") // Retrieve division from intent extras
        sem = b?.getString("sem") // Retrieve division from intent extras
        lecture = b?.getString("lecture") // Retrieve division from intent extras
        Log.e("tableaa","$a")
        Log.d("Menuuuuu",menuid)
        Log.e("lectureaaa","$lecture")
        Log.e("csv",division+sem)
        calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val dayOfmonth = calendar.get(Calendar.DAY_OF_MONTH)
        val dayOfMonth = dayOfmonth
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        val date1 = "$dayOfMonth/$month/$year"
        Log.d("DateeeeOfa","$date1")
        new_column = date1+"_"+lecture
        Log.d("New", new_column!!)
        try {
            val inputStream = assets.open("${division.toString()}"+"${sem.toString()}.csv")
            val csvReader = CSVReader(InputStreamReader(inputStream))
            var nextLine: Array<String>?
            while (csvReader.readNext().also { nextLine = it } != null) {
                val enrollmentNumber = nextLine?.get(0) ?: ""

                val name = nextLine?.get(1) ?: ""
                val url = "http://192.168.2.84/insertData.php"
                val backgroundWorker = BackgroundWorker(this) { result ->
                    // Handle result from BackgroundWorker
                    if (result == "ColumnAlreadyExists") {
                        showToast("Column already exists in the database")
                    } else {
                        executeNextTask()
                    }
                }
                tasksQueue.add(backgroundWorker)
                backgroundWorker.execute(url, a, enrollmentNumber, name,new_column)
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
        val b = Bundle()
        b.putString("table",a)
        b.putString("lecture",lecture)
        b.putString("new_column",new_column)
        b.putString("menuId",menuid)
        b.putString("sem",sem)
        intent.putExtras(b)
        startActivity(intent)
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
