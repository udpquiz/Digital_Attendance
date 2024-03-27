        package com.example.digital_attendance
//        import BackgroundWorker
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
                lecture = b?.getString("lecture") // Retrieve division from intent extras
                Log.e("tableaa","$a")
                Log.d("Menuuuuu",menuid)
                Log.e("lectureaaa","$lecture")
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
                    val inputStream = assets.open("${division.toString()}.csv")
                    val csvReader = CSVReader(InputStreamReader(inputStream))
                    var nextLine: Array<String>?
                    while (csvReader.readNext().also { nextLine = it } != null) {
                        val enrollmentNumber = nextLine?.get(0) ?: ""
                        
                        val name = nextLine?.get(1) ?: ""
                        val url = "http://192.168.61.243/insertData.php"
                        val backgroundWorker = BackgroundWorker(this) {
                            executeNextTask()
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
                intent.putExtras(b)
                startActivity(intent)
            }
        }