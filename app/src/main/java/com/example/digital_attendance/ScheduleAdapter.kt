package com.example.digital_attendance

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ScheduleAdapter(private val context: Context) : RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {
    private var data: List<Schedule1> = ArrayList()
    private var onDeleteClickListener: ((String) -> Unit)? = null

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

        holder.itemView.setOnClickListener {
            // Open ScheduleUpdateActivity with the selected schedule data
            val intent = Intent(context, ScheduleUpdateActivity::class.java)
            intent.putExtra("schedule", schedule) // Pass the selected schedule object
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setOnDeleteClickListener(listener: (String) -> Unit) {
        onDeleteClickListener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textTitle: TextView = itemView.findViewById(R.id.textTitle)
        private val btnDelete: Button = itemView.findViewById(R.id.delete)

        init {
            btnDelete.setOnClickListener {
                onDeleteClickListener?.invoke(data[adapterPosition].id)
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(schedule: Schedule1) {
            textTitle.text =
                "ID : ${schedule.id}\n"+
                "LEC_NO : ${schedule.LEC_NO}\n"+
                        "Date: ${schedule.date} \nSem: ${schedule.sem}\nDiv: ${schedule.division}\nStart Time: ${schedule.start_time}\n"+
                        "End Time: ${schedule.end_time}\n" +
                        "Subject: ${schedule.sub_name}\n" +
                        "Faculty: ${schedule.f_name}\n" +
                        "Room: ${schedule.room}"
        }
    }
}
