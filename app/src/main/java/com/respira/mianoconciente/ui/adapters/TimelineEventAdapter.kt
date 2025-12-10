package com.respira.mianoconciente.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.respira.mianoconciente.R
import com.respira.mianoconciente.data.local.TimelineEvent
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TimelineEventAdapter(private val items: List<TimelineEvent>) :
    RecyclerView.Adapter<TimelineEventAdapter.TimelineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_timeline_event, parent, false)
        return TimelineViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TimelineViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class TimelineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvEventTitle)
        private val tvEmotion: TextView = itemView.findViewById(R.id.tvEventEmotion)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvEventDescription)
        private val tvDate: TextView = itemView.findViewById(R.id.tvEventDate)
        private val formatter = SimpleDateFormat("dd MMM", Locale.getDefault())

        fun bind(event: TimelineEvent) {
            tvTitle.text = event.title
            tvEmotion.text = event.mainEmotion
            tvDescription.text = event.description ?: ""
            tvDate.text = formatter.format(Date(event.date))
        }
    }
}
