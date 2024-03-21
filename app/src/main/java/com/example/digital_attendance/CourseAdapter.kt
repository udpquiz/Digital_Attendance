package com.example.digital_attendance

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CourseAdapter(private val context: Context):RecyclerView.Adapter<CourseAdapter.ViewHolder>() {

    private var courseList: List<Course> = ArrayList()

    fun setData(data: List<Course>) {
        this.courseList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_course_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val course = courseList[position]
        holder.bind(course)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val codeTextView: TextView = itemView.findViewById(R.id.c_codeTextView)
        private val nameTextView: TextView = itemView.findViewById(R.id.c_nameTextView)
        private val semTextView: TextView = itemView.findViewById(R.id.c_semTextView)

        fun bind(course: Course) {
            codeTextView.text = course.code
            nameTextView.text = course.name
            semTextView.text = course.sem
        }
    }
}