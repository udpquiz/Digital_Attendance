package com.example.digital_attendance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ScheduleAdapter: RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {
    private var data: List<Schedule1> = ArrayList()

    fun setData(data: List<Schedule1>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_schedule_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val schedule = data[position]
        holder.bind(schedule)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textTitle: TextView = itemView.findViewById(R.id.textTitle)
        // Bind other TextViews here for other data fields

        fun bind(schedule: Schedule1) {
            textTitle.text =
                "ID : ${schedule.id}\n"+
                "Date: ${schedule.date} \nSem: ${schedule.sem}\n Div: ${schedule.division}\nStart Time: ${schedule.start_time}\n" +
                    " End Time: ${schedule.end_time}\n" +
                    " Subject: ${schedule.sub_name}\n" +
                    " Faculty: ${schedule.f_name}\n" +
                    " Room: ${schedule.room}" // Modify according to your data fields
            // Bind other data fields similarly
        }
    }
}