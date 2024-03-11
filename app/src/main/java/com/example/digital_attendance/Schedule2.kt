package com.example.digital_attendance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
    }

    override fun getItemCount(): Int {
        return schedules.size
    }

    fun addSchedule(schedule: String) {
        schedules.add(schedule)
        notifyDataSetChanged()
    }
}
