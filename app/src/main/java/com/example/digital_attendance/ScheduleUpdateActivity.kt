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
    lateinit var sub: Spinner
    lateinit var suba: ArrayAdapter<String>
    val from = arrayOf("8:00", "8:55", "9:50", "10:45")
    val to = arrayOf("8:50", "9:45", "10:40", "11:35")
    lateinit var efrom: EditText
    lateinit var eto: EditText
    lateinit var semEditText: EditText




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_update)
        val schedule = intent.getSerializableExtra("schedule") as Schedule1

        val faculty_list = ArrayList<String>()
//        val id = findViewById<EditText>(R.id.id1)
        val db = LJCRUD1(this)
        c = db.viewFaculty()!!
        while (c.moveToNext()) {
            val value = c.getString(1)
            faculty_list.add(value)
        }


        // Find views by their IDs
        val sidTextView = findViewById<TextView>(R.id.sid)
        val spin_lec: Spinner = findViewById(R.id.spin_lec)
        dateTextView = findViewById<TextView>(R.id.date1)
        val toEditText = findViewById<EditText>(R.id.eto)
        val fromEditText = findViewById<EditText>(R.id.efrom)
        val roomSpinner = findViewById<Spinner>(R.id.room)
        val subSpinner = findViewById<Spinner>(R.id.sub)
        val facultySpinner = findViewById<Spinner>(R.id.faculty)
        val classSpinner = findViewById<Spinner>(R.id.class1)
        semEditText = findViewById(R.id.sem)
        val btnSave = findViewById<Button>(R.id.btnsave)
        sub = findViewById(R.id.sub)
        efrom = findViewById(R.id.efrom)
        eto = findViewById(R.id.eto)
//        val db=LJCRUD1(this)
        val lectures = arrayOf("Lec 1", "Lec 2", "Lec 3", "Lec 4")

        semEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val sem2 = s.toString()

                val course_list = ArrayList<String>()
                c1 = db.viewsub(sem2)!!
                while (c1.moveToNext()) {
                    val value = c1.getString(2)
                    course_list.add(value)
                }
//                Toast.makeText(applicationContext, "$course_list", Toast.LENGTH_SHORT).show()
                suba = ArrayAdapter<String>(
                    this@ScheduleUpdateActivity,
                    android.R.layout.select_dialog_item,
                    course_list
                )
                sub.setAdapter(suba)

            }

            override fun afterTextChanged(s: Editable?) {

            }

        })



        // Set values to views based on the selected schedule data
        sidTextView.text = schedule.id
        dateTextView.text = schedule.date
        toEditText.setText(schedule.end_time)
        fromEditText.setText(schedule.start_time)
        semEditText.setText(schedule.sem)

        // Populate spinners with data from the database
        // Assuming you have methods to retrieve data for each spinner
        val roomData = arrayOf("Lab-1","Lab-2","Lab-3","110", "105", "212","215",) // Replace with actual room data
//        val subData = arrayOf("Subject1", "Subject2", "Subject3") // Replace with actual subject data
//        val facultyData = arrayOf("Faculty1", "Faculty2", "Faculty3") // Replace with actual faculty data
        val classData = arrayOf("ICA-A","ICA-B","ICA-C","ICA-D","ICA-E") // Replace with actual class data

//        populateSpinner(roomSpinner, roomData)
//        populateSpinner(subSpinner, subData)
//        populateSpinner(facultySpinner, facultyData)
//        populateSpinner(classSpinner, classData)

        val faca: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.select_dialog_item, faculty_list)
        facultySpinner.setAdapter(faca)
        val rooma: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.select_dialog_item, roomData)
        roomSpinner.setAdapter(rooma)
        val classa: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.select_dialog_item, classData)
        classSpinner.setAdapter(classa)
        val sl: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.select_dialog_item, lectures)
        spin_lec.setAdapter(sl)

        spin_lec.onItemSelectedListener = this



        // Handle save button click
        btnSave.setOnClickListener {
            // Get updated values from the views
            val updatedSchedule = Schedule1(
                schedule.id,
                dateTextView.text.toString(),
                semEditText.text.toString(),
                classSpinner.selectedItem.toString(), // Get division value from appropriate view
                fromEditText.text.toString(),
                toEditText.text.toString(),
                subSpinner.selectedItem.toString(),
                facultySpinner.selectedItem.toString(),
                roomSpinner.selectedItem.toString()
            )

            // Call update function of your database helper
            val db = LJCRUD1(this)
            val isUpdated = db.updateSchedule(updatedSchedule)

            // Show appropriate message based on update status
            if (isUpdated) {
                // Update successful
                Toast.makeText(this, "Update hogaya", Toast.LENGTH_SHORT).show()
                // You can also navigate back to the previous activity if needed
                startActivity(Intent(this,View_Schedule::class.java))

            } else {
                // Update failed
                Toast.makeText(this, "Nhi hua", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle view button click
    }

    private fun populateSpinner(spinner: Spinner, data: Array<String>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, data)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
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

            R.id.room -> {
                // Handle selection for room spinner
                Toast.makeText(this, "Selected Room: $selectedItem", Toast.LENGTH_SHORT).show()
                println(selectedItem)
            }

            R.id.faculty -> {
                // Handle selection for faculty spinner
                Toast.makeText(this, "Selected Faculty: $selectedItem", Toast.LENGTH_SHORT)
                    .show()
                println(selectedItem)
            }

            R.id.class1 -> {
                // Handle selection for class1 spinner
                Toast.makeText(this, "Selected Class: $selectedItem", Toast.LENGTH_SHORT).show()
                println(selectedItem)
            }

            R.id.sem -> {
                val selectedSemester = semEditText.toString()
                val db = LJCRUD1(this)
                val subjectsCursor = db.viewsub(selectedSemester)
                val courseList = ArrayList<String>()
                subjectsCursor?.let {
                    while (it.moveToNext()) {
                        val value = it.getString(2)
                        courseList.add(value)
                    }
                }
                val subAdapter =
                    ArrayAdapter(this, android.R.layout.select_dialog_item, courseList)
                sub.setAdapter(subAdapter)
            }

            R.id.sub -> {
                // Handle selection for sub spinner
                Toast.makeText(this, "Selected Subject: $selectedItem", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}