package com.example.digital_attendance.com.example.digital_attendance

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.digital_attendance.R

class FacultyAdapter(private val context: Context) : RecyclerView.Adapter<FacultyAdapter.ViewHolder>() {

    private var facultyList: List<Faculty> = ArrayList()

    fun setData(data: List<Faculty>) {
        this.facultyList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_faculty_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return facultyList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val faculty = facultyList[position]
        holder.bind(faculty)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val codeTextView: TextView = itemView.findViewById(R.id.f_codeTextView)
        private val nameTextView: TextView = itemView.findViewById(R.id.f_nameTextView)
        private val emailTextView: TextView = itemView.findViewById(R.id.f_emailTextView)
        private val passwordTextView: TextView = itemView.findViewById(R.id.f_passwordTextView)

        fun bind(faculty: Faculty) {
            codeTextView.text = faculty.code
            nameTextView.text = faculty.name
            emailTextView.text = faculty.email
            passwordTextView.text = faculty.password
        }
    }
}