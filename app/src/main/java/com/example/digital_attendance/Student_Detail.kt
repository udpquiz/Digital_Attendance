package com.example.digital_attendance

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast

class Student_Detail : AppCompatActivity() {
    private val sharedfile = "student_details"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_detail)
        val sem:Spinner=findViewById(R.id.spin_sem)
        val div:Spinner=findViewById(R.id.spin_div)
        val btn:Button=findViewById(R.id.btn)
        val b = intent.extras
        val semester= arrayOf("1","2","3","4","5","6")
        val division= arrayOf("ICA_A","ICA_B","ICA_C","ICA_D","ICA_E")
        val sp: SharedPreferences =this.getSharedPreferences(sharedfile, Context.MODE_PRIVATE)


        val ss: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.select_dialog_item, semester)
        sem.setAdapter(ss)
        val dd: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.select_dialog_item, division)
        div.setAdapter(dd)


        btn.setOnClickListener {
            val enrollment=b?.getString("enrollment")
            val selectedsem = sem.selectedItem.toString()
            val selecteddiv = div.selectedItem.toString()
            Log.d("SEM", "$selectedsem")
            Log.d("DIV", "$selecteddiv")
            val editor = sp.edit()

            editor.putString("enrollment",enrollment)
            editor.putString("semester",selectedsem)
            editor.putString("division",selecteddiv)
            editor.apply()
            editor.commit()
            Toast.makeText(this,"Data Saved",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,Student_Page::class.java))
        }

    }
}