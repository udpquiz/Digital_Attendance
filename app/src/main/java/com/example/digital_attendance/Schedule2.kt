package com.example.digital_attendance

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.digital_attendance.R

class Schedule2 : RecyclerView.Adapter<Schedule2.ScheduleViewHolder>() {
    private val schedules = ArrayList<String>()
    private var menuId = 0

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
        holder.itemView.setOnClickListener { view ->
            val popupMenu = PopupMenu(view.context, view)
            popupMenu.inflate(R.menu.popup_menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.new_Attendance -> {
                        menuId = 1
                        handleMenuAction(view, schedule)
                        true
                    }
                    R.id.SaveAsLast -> {
                        menuId = 2
                        handleMenuAction(view, schedule)

                        true
                    }
                    R.id.EditAsLast -> {
                        menuId = 3
                        handleMenuAction(view, schedule)
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }
    }

    private fun handleMenuAction(view: View, schedule: String) {
        val details = schedule.split("\n")
        val division = details[3].substring(11)
        val sem = details[2].substring(6)
        val subject = details[6].substring(10)
        val lecture = details[8].substring(12)
        Log.d("lecture", "$lecture")
        val message = "$division$sem$subject"
        val table = "$division$sem$subject"
        Log.e("FileName", "$division.csv")
        Log.e("FileName", "$division$sem$subject")
        Toast.makeText(view.context, message, Toast.LENGTH_SHORT).show()
        val intent = Intent(view.context, phpUplaod::class.java)
        intent.putExtra("division", division)
        intent.putExtra("lecture", lecture)
        intent.putExtra("table", table)
        intent.putExtra("MenuId", menuId) // Pass the MenuId to the next activity
        view.context.startActivity(intent)
    }

    override fun getItemCount(): Int {
        return schedules.size
    }

    fun addSchedule(schedule: String) {
        schedules.add(schedule)
        notifyDataSetChanged()
    }
}
