package com.respira.mianoaconsciente.ui.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.respira.mianoaconsciente.R
import com.respira.mianoaconsciente.data.local.entities.VisionCard

class VisionCardAdapter(private var items: List<VisionCard>) :
    RecyclerView.Adapter<VisionCardAdapter.VisionCardViewHolder>() {

    fun update(newItems: List<VisionCard>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisionCardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vision_card, parent, false)
        return VisionCardViewHolder(view)
    }

    override fun onBindViewHolder(holder: VisionCardViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class VisionCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvVisionTitle)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvVisionDescription)

        fun bind(card: VisionCard) {
            tvTitle.text = card.title
            tvDescription.text = card.description
        }
    }
}
