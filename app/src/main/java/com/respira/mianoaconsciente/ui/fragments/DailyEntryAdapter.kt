package com.respira.mianoaconsciente.ui.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.respira.mianoaconsciente.R
import com.respira.mianoaconsciente.data.local.entities.DailyEntry
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DailyEntryAdapter(private var items: List<DailyEntry>) :
    RecyclerView.Adapter<DailyEntryAdapter.DailyEntryViewHolder>() {

    fun update(newItems: List<DailyEntry>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyEntryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_daily_entry, parent, false)
        return DailyEntryViewHolder(view)
    }

    override fun onBindViewHolder(holder: DailyEntryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class DailyEntryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        private val tvMood: TextView = itemView.findViewById(R.id.tvMood)
        private val tvNote: TextView = itemView.findViewById(R.id.tvNote)

        fun bind(entry: DailyEntry) {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            tvDate.text = dateFormat.format(Date(entry.date))
            tvMood.text = "Estado: ${entry.mood}"
            tvNote.text = entry.note ?: ""
        }
    }
}
