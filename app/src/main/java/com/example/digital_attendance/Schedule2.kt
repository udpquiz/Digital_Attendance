package com.example.digital_attendance

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.digital_attendance.R

class Schedule2 : RecyclerView.Adapter<Schedule2.ScheduleViewHolder>() {
    private val schedules = ArrayList<String>()

    class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val scheduleDetail: TextView = itemView.findViewById(R.id.scheduleDetail1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_schedule_item1, parent, false)
        return ScheduleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val schedule = schedules[position]
        holder.scheduleDetail.text = schedule
        holder.itemView.setOnClickListener {
            // Extract schedule details from the displayed string
            val details = schedule.split("\n")
            val division = details[3].substring(11)
            val division1 = details[3]
            val sem = details[2].substring(6)
            val subject = details[6].substring(10)
            val message = "$division"+"$sem"+"$subject"
            val table = "$division$sem$subject"
            val b = Bundle()
            b.putString("division",division)
            b.putString("table",table)
            Log.e("FileName","$division1")
            Log.e("FileName","$division.csv")
            Log.e("FileName","$division$sem$subject")
            Toast.makeText(it.context, message, Toast.LENGTH_SHORT).show()
            val context = it.context
            val intent = Intent(context,phpUplaod::class.java)
            intent.putExtras(b)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return schedules.size
    }

    fun addSchedule(schedule: String) {
        schedules.add(schedule)
        notifyDataSetChanged()
    }
}
