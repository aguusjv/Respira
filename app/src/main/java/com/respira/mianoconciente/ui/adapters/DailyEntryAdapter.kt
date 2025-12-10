package com.respira.mianoconciente.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.respira.mianoconciente.R
import com.respira.mianoconciente.data.local.DailyEntry
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DailyEntryAdapter(private val items: List<DailyEntry>) :
    RecyclerView.Adapter<DailyEntryAdapter.EntryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_daily_entry, parent, false)
        return EntryViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class EntryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        private val tvMood: TextView = itemView.findViewById(R.id.tvMood)
        private val tvNote: TextView = itemView.findViewById(R.id.tvNote)
        private val tvMicro: TextView = itemView.findViewById(R.id.tvMicro)
        private val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

        fun bind(entry: DailyEntry) {
            tvDate.text = formatter.format(Date(entry.date))
            tvMood.text = "Estado: ${entry.mood}/5"
            tvNote.text = entry.note?.let { "Nota: $it" } ?: ""
            tvMicro.text = entry.microAction?.let { "Micro-acción: $it" } ?: ""
        }
    }
}
