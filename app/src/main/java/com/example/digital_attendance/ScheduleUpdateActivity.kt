package com.example.digital_attendance

import android.app.DatePickerDialog
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

class ScheduleUpdateActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var dateTextView: TextView
    lateinit var c: Cursor
    lateinit var c1: Cursor
    lateinit var c2: Cursor
    lateinit var c3: Cursor
    lateinit var sub: Spinner
    lateinit var suba: ArrayAdapter<String>
    val from = arrayOf("8:00", "8:55", "9:50", "10:45")
    val to = arrayOf("8:50", "9:45", "10:40", "11:35")
    lateinit var efrom: EditText
    lateinit var eto: EditText
//    lateinit var sem1: Spinner
    var subdata = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_update)
        val schedule = intent.getSerializableExtra("schedule") as Schedule1

        // Find views by their IDs
        val sidTextView = findViewById<TextView>(R.id.sid)
        val spin_lec: Spinner = findViewById(R.id.spin_lec)
        spin_lec.onItemSelectedListener=this
        dateTextView = findViewById<TextView>(R.id.date1)
        val toEditText = findViewById<EditText>(R.id.eto)
        val fromEditText = findViewById<EditText>(R.id.efrom)
        val roomSpinner = findViewById<Spinner>(R.id.room)
//        val subSpinner = findViewById<Spinner>(R.id.sub)
        val facultySpinner = findViewById<Spinner>(R.id.faculty)
        val classSpinner = findViewById<Spinner>(R.id.class1)
        val sem1 = findViewById<Spinner>(R.id.sem)
        val btnSave = findViewById<Button>(R.id.btnsave)
        sub = findViewById(R.id.sub)
        efrom = findViewById(R.id.efrom)
        eto = findViewById(R.id.eto)

        // Initialize your database helper
        val db = LJCRUD1(this)
        val roomData = ArrayList<String>()
        c2 = db.viewroom()!!
        while (c2.moveToNext()) {
            val value = c2.getString(1)
            roomData.add(value)
        }
        val course_list = ArrayList<String>()
        c1 = db.viewsub("4")!!
        while (c1.moveToNext()) {
            val value = c1.getString(2)
            course_list.add(value)
        }
        val aclass = ArrayList<String>()
        c3 = db.viewdiv()!!
        while (c3.moveToNext()) {
            val value = c3.getString(1)
            aclass.add(value)
        }

        // Populate spinners with data from the database
        val lectures = arrayOf("Lec 1", "Lec 2", "Lec 3", "Lec 4")
        val classData = arrayOf("ICA_A", "ICA_B", "ICA_C", "ICA_D", "ICA_E") // Replace with actual class data
        val semesters = arrayOf("1", "2", "3", "4","5","6")

        val sl: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.select_dialog_item, lectures)
        spin_lec.setAdapter(sl)
        val rooma: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.select_dialog_item, roomData)
        roomSpinner.setAdapter(rooma)

        val classa: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.select_dialog_item, aclass)
        classSpinner.setAdapter(classa)

        // Assuming you have methods to retrieve data for each spinner
        val facultyList = ArrayList<String>()
        val c = db.viewFaculty()!!
        while (c.moveToNext()) {
            val value = c.getString(1)
            facultyList.add(value)
        }
        val faca: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.select_dialog_item, facultyList)
        facultySpinner.setAdapter(faca)
        val sema: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.select_dialog_item, semesters)
        sem1.setAdapter(sema)
        suba = ArrayAdapter<String>(
            this@ScheduleUpdateActivity,
            android.R.layout.select_dialog_item
        )
        sub.setAdapter(suba)

        // Set saved values to spinners
        val roomPosition = roomData.indexOf(schedule.room)
        if (roomPosition != -1) {
            roomSpinner.setSelection(roomPosition)
        }


        val classPosition = classData.indexOf(schedule.division)
        if (classPosition != -1) {
            classSpinner.setSelection(classPosition)
        }
        val lecPosition = lectures.indexOf(schedule.LEC_NO)
        if (lecPosition != -1) {
            spin_lec.setSelection(lecPosition)
        }
        val semPosition = semesters.indexOf(schedule.sem)
        if (semPosition != -1) {
            sem1.setSelection(semPosition)
        }

//        suba = ArrayAdapter<String>(
//            this@ScheduleUpdateActivity,
//            android.R.layout.select_dialog_item
//        )

        val facultyPosition = facultyList.indexOf(schedule.f_name)
        if (facultyPosition != -1) {
            facultySpinner.setSelection(facultyPosition)
        }

//        sem1.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                val semValue = s.toString()
//                c3 = db.viewsub(semValue)!!
//                subdata.clear() // Clear previous data before adding new values
//                while (c3.moveToNext()) {
//                    val value = c3.getString(2)
//                    subdata.add(value)
//                }
//                // Update the adapter with new data
//                suba.notifyDataSetChanged()
//
//                // Set the selection after updating the data
//                val subPosition = subdata.indexOf(schedule.sub_name)
//                if (subPosition != -1) {
//                    sub.setSelection(subPosition)
//                }
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//            }
//        })
//        sub.adapter = suba
        sem1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedSemester = semesters[position]

                val courseList = ArrayList<String>()
                c1 = db.viewsub(selectedSemester)!!
                while (c1.moveToNext()) {
                    val value = c1.getString(2)
                    courseList.add(value)
                }
//                suba = ArrayAdapter<String>(
//                    this@ScheduleUpdateActivity,
//                    android.R.layout.select_dialog_item
//                )
//                sub.setAdapter(suba)


                suba.clear()
                suba.addAll(courseList)
                suba.notifyDataSetChanged()
                val subPosition = courseList.indexOf(schedule.sub_name)
                if (subPosition != -1) {
                    sub.setSelection(subPosition)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        // Other code for initializing and setting values to views...

        // Set values to views based on the selected schedule data
        sidTextView.text = schedule.id
        dateTextView.text = schedule.date
        eto.setText(schedule.end_time)
        efrom.setText(schedule.start_time)
//        sem1.selectedItem.toString(schedule.sem)

        // Handle save button click
        btnSave.setOnClickListener {
            // Get updated values from the views
            val updatedSchedule = Schedule1(
                schedule.id,
                dateTextView.text.toString(),
                sem1.selectedItem.toString(),
                classSpinner.selectedItem.toString(), // Get division value from appropriate view
                fromEditText.text.toString(),
                toEditText.text.toString(),
                sub.selectedItem.toString(),
                facultySpinner.selectedItem.toString(),
                roomSpinner.selectedItem.toString(),
                spin_lec.selectedItem.toString()
            )

            // Call update function of your database helper
            val db = LJCRUD1(this)
            val isUpdated = db.updateSchedule(updatedSchedule)

            // Show appropriate message based on update status
            if (isUpdated) {
                // Update successful
                Toast.makeText(this, "Successfully Updated", Toast.LENGTH_SHORT).show()
                // You can also navigate back to the previous activity if needed
                startActivity(Intent(this, View_Schedule::class.java))

            } else {
                // Update failed
                Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle view button click

    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, View_Schedule::class.java))
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        // Handle spinner item selection
        val spinner = parent as Spinner
        val selectedItem = spinner.selectedItem.toString()
        when (spinner.id) {
            R.id.spin_lec -> {
                // Handle selection for spin_lec spinner
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
        // Handle when nothing is selected in spinner
    }
}
