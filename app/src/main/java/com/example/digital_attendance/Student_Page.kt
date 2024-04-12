package com.example.digital_attendance

import android.app.DatePickerDialog
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Calendar

class Student_Page : AppCompatActivity() {
    lateinit var c: Cursor
    lateinit var aa: ScheduleAdapter
    lateinit var tsem: TextView
    lateinit var tdiv: TextView
    lateinit var sem: String
    lateinit var div: String
    lateinit var tablename: String
    lateinit var columnname: String
    lateinit var enrollment: String
    lateinit var lecture: String


    private lateinit var date1: TextView
    private lateinit var calendar: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_page)

        val sp = getSharedPreferences("student_details", MODE_PRIVATE)

        val b = intent.extras
        val t = findViewById<TextView>(R.id.t)
        tsem = findViewById<TextView>(R.id.tsem)
        tdiv = findViewById<TextView>(R.id.tdiv)
        date1 = findViewById(R.id.date1)
        calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val dayOfmonth = calendar.get(Calendar.DAY_OF_MONTH)
//        val dayOfMonth = dayOfmonth + 1
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        date1.text = "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH)+1}/${calendar.get(Calendar.YEAR)}"
        date1.setOnClickListener {
            showDatePickerDialog()
        }
        t.text = b?.getString("enrollment")
        val ab = b?.getString("enrollment")
        Log.d("abc",ab.toString())
        t.setText(sp.getString("enrollment", "defaultt"))
        enrollment = sp.getString("enrollment", "defaultt").toString()
        tsem.setText(sp.getString("semester", "defaultt"))
        sem = sp.getString("semester", "defaultt").toString()
        div = sp.getString("division", "defaultt").toString()
        tdiv.setText(sp.getString("division", "defaultt"))
//        Toast.makeText(this,"$tablename",Toast.LENGTH_SHORT).show()
        val sem1 = tsem.text.toString()
        val div1 = tdiv.text.toString()
        val db = LJCRUD1(this)
        c = db.student_schedule(date1.text.toString(), sem1, div1)!!
        aa = ScheduleAdapter()
        c.moveToFirst()
        while (!c.isAfterLast()) {
            val dates = c.getString(1)
            val subjects = c.getString(6)
            val lectures = c.getString(9)
            Log.d("Aaaaaaa", dates)
            Log.d("Aaaaaaa", subjects)
            Log.d("Aaaaaaa", lectures)
            aa.addItem("Date:${c.getString(1)}\n" +
                    "Start Time: ${c.getString(4)}\n" +
                    "End Time: ${c.getString(5)}\n" +
                    "Subject: ${c.getString(6)}\nFac: ${c.getString(7)}\n" +
                    "Room: ${c.getString(8)}\n" + "Lec No : ${c.getString(9)}")
            Log.d("looo", "${c.getString(9)}")
            c.moveToNext()
        }
        c.close()

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = aa

    }

    fun updateDataOnDateChange(selectedDate: String) {
        val sem1 = tsem.text.toString()
        val div1 = tdiv.text.toString()
        val db = LJCRUD1(this)
        c = db.student_schedule(selectedDate, sem1, div1)!!
        aa.clearItems() // Clear previous data
        c.moveToFirst()
        while (!c.isAfterLast()) {
            aa.addItem("Date:${c.getString(1)}\n" +
                    "Start Time: ${c.getString(4)}\n" +
                    "End Time: ${c.getString(5)}\n" +
                    "Subject: ${c.getString(6)}\nFac: ${c.getString(7)}\n" +
                    "Room: ${c.getString(8)}\n" + "Lec No : ${c.getString(9)}")
            c.moveToNext()
        }
        c.close()
        aa.notifyDataSetChanged() // Notify adapter that data has changed
    }

    fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            this,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                // Save the selected date
                val selectedDate = "$dayOfMonth/${month + 1}/$year"
                date1.text = selectedDate
                updateDataOnDateChange(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        // maaz
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()
    }

    inner class ScheduleAdapter : RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {
        private val scheduleList: MutableList<String> = ArrayList()

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textView: TextView = itemView.findViewById(R.id.text_schedule_date)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_schedule_cardview, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = scheduleList[position]
            holder.textView.text = item

            holder.itemView.setOnClickListener {
                var selectedDate = item.split("\n")[0].substringAfter("Date:")
                selectedDate = selectedDate.replace("/","_")
                var selectedlec = item.split("\n")[6].substringAfter("Lec No : ")
                selectedlec = selectedlec.replace(" ","_")
                val selectedSubject = item.split("\n")[3].substringAfter("Subject: ")
                tablename = "${div}${sem}_${selectedSubject}"
                columnname = "${selectedDate}_${selectedlec}"
                val url = "http://192.168.61.243/fetchAttendance.php"
                BackgroundWorker2(this@Student_Page){
                }.execute(url,tablename,columnname,enrollment)
            }

        }

        override fun getItemCount(): Int {
            return scheduleList.size
        }

        fun addItem(item: String) {
            scheduleList.add(item)
            notifyItemInserted(scheduleList.size - 1)
        }

        fun clearItems() {
            scheduleList.clear()
            notifyDataSetChanged()
        }
    }
}
