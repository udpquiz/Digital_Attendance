package com.example.digital_attendance

import android.app.DatePickerDialog
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import java.util.Calendar

class Add_Schedule : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var c: Cursor
    lateinit var c1: Cursor
    lateinit var c2: Cursor
    lateinit var c3: Cursor
    lateinit var suba: ArrayAdapter<String>
    private lateinit var date1: TextView
    private var selectedsem = ""
    var ab: String = ""
    private lateinit var calendar: Calendar
    lateinit var btnsave: Button
    lateinit var sem1: Spinner
    lateinit var sub: Spinner
    val from = arrayOf("8:00", "8:55", "9:50", "10:45")
    val to = arrayOf("8:50", "9:45", "10:40", "11:35")
    lateinit var efrom: EditText
    lateinit var eto: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_schedule)

        val faculty_list = ArrayList<String>()
        val id = findViewById<EditText>(R.id.id1)
        val db = LJCRUD1(this)
        c = db.viewFaculty()!!
        while (c.moveToNext()) {
            val value = c.getString(1)
            faculty_list.add(value)
        }
        val course_list = ArrayList<String>()
        c1 = db.viewsub("4")!!
        while (c1.moveToNext()) {
            val value = c1.getString(2)
            course_list.add(value)
        }
        val aroom = ArrayList<String>()
        c2 = db.viewroom()!!
        while (c2.moveToNext()) {
            val value = c2.getString(1)
            aroom.add(value)
        }
        val aclass = ArrayList<String>()
        c3 = db.viewdiv()!!
        while (c3.moveToNext()) {
            val value = c3.getString(1)
            aclass.add(value)
        }

        efrom = findViewById(R.id.efrom)
        eto = findViewById(R.id.eto)

        val spin_lec: Spinner = findViewById(R.id.spin_lec)
        val room: Spinner = findViewById(R.id.room)
        val faculty: Spinner = findViewById(R.id.faculty)
        val class1: Spinner = findViewById(R.id.class1)
        sem1 = findViewById(R.id.sem)
        sub = findViewById(R.id.sub)

        val lectures = arrayOf("Lec 1", "Lec 2", "Lec 3", "Lec 4")

        btnsave = findViewById(R.id.btnsave)
        date1 = findViewById(R.id.date1)
        calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val dayOfmonth = calendar.get(Calendar.DAY_OF_MONTH)

        calendar.add(Calendar.DAY_OF_MONTH, 1)
        date1.text = "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}"
        date1.setOnClickListener {
            showDatePickerDialog()
        }

        val semesters = arrayOf("1", "2", "3", "4","5","6")

        val semAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, semesters)
        semAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sem1.adapter = semAdapter

        sem1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedSemester = semesters[position]

                val courseList = ArrayList<String>()
                c1 = db.viewsub(selectedSemester)!!
                while (c1.moveToNext()) {
                    val value = c1.getString(2)
                    courseList.add(value)
                }
                suba = ArrayAdapter<String>(
                    this@Add_Schedule,
                    android.R.layout.select_dialog_item,
                    course_list
                )
                sub.setAdapter(suba)

                suba.clear()
                suba.addAll(courseList)
                suba.notifyDataSetChanged()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        val sl: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.select_dialog_item, lectures)
        spin_lec.setAdapter(sl)

        val rooma: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.select_dialog_item, aroom)
        room.setAdapter(rooma)

        val faca: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.select_dialog_item, faculty_list)
        faculty.setAdapter(faca)

        val classa: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.select_dialog_item, aclass)
        class1.setAdapter(classa)
        spin_lec.onItemSelectedListener=this

        btnsave.setOnClickListener {
            val selectedfac = faculty.selectedItem.toString()
            selectedsem = sem1.selectedItem.toString()
            ab = selectedsem
            val selecteddiv = class1.selectedItem.toString()
            val selectedsub = sub.selectedItem.toString()
            val selectedroom = room.selectedItem.toString()
            val selectedlec = spin_lec.selectedItem.toString()
            val c = db.viewsc(date1.text.toString())

            if (c != null && c.count > -1) {
                val schedules = mutableListOf<Schedule1>()
                c.moveToFirst()
                while (!c.isAfterLast) {
                    val schedule = Schedule1(
                        c.getString(0),
                        c.getString(1),
                        c.getString(2),
                        c.getString(3),
                        c.getString(4),
                        c.getString(5),
                        c.getString(6),
                        c.getString(7),
                        c.getString(8),
                        c.getString(9)
                    )
                    schedules.add(schedule)
                    c.moveToNext()
                }
                val lectureExists = schedules.any {
                    it.date == date1.text.toString() &&
                            it.sem == selectedsem &&
                            it.division == selecteddiv &&
                            it.start_time == efrom.text.toString() &&
                            it.end_time == eto.text.toString() &&
                            it.sub_name == selectedsub &&
                            it.f_name == selectedfac &&
                            it.room == selectedroom
                }
                val faculty_Busy = schedules.any {
                    it.date == date1.text.toString() &&
                            it.start_time == efrom.text.toString() &&
                            it.end_time == eto.text.toString() &&
                            it.f_name == selectedfac
                }
                if (lectureExists) {
                    Toast.makeText(this, "The Lecture is Already Scheduled", Toast.LENGTH_SHORT).show()
                } else if (faculty_Busy) {
                    Toast.makeText(this, "Faculty Have Lecture On Same Time", Toast.LENGTH_SHORT).show()
                } else {
                    val r: Boolean = db.inserttt(
                        date1.text.toString(),
                        sem1.selectedItem.toString(),
                        selecteddiv,
                        efrom.text.toString(),
                        eto.text.toString(),
                        selectedsub,
                        selectedfac,
                        selectedroom,
                        selectedlec
                    )
                    if (r == true) {
                        Toast.makeText(this, "Schedule Saved!!!!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, Admin_Dashboard::class.java))
                    } else {
                        Toast.makeText(this, "FAILED ????", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            this,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                val selectedDate = "$dayOfMonth/${month + 1}/$year"
                date1.text = selectedDate
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val spinner = parent as Spinner
        val selectedItem = spinner.selectedItem.toString()
        when (spinner.id) {
            R.id.spin_lec -> {
                when (selectedItem) {
                    "Lec 1" -> {
                        efrom.setText(from[0])
                        eto.setText(to[0])
                    }
                    "Lec 2" -> {
                        efrom.setText(from[1])
                        eto.setText(to[1])
                    }
                    "Lec 3" -> {
                        efrom.setText(from[2])
                        eto.setText(to[2])
                    }
                    "Lec 4" -> {
                        efrom.setText(from[3])
                        eto.setText(to[3])
                    }
                }
            }
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
}
