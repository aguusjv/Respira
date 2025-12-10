package com.respira.mianoconciente.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.respira.mianoconciente.R
import com.respira.mianoconciente.data.local.VisionCard

class VisionCardAdapter(private val items: List<VisionCard>) :
    RecyclerView.Adapter<VisionCardAdapter.VisionViewHolder>() {

    private val colors = listOf(
        R.color.soft_green,
        R.color.soft_rose,
        R.color.soft_mustard,
        R.color.accent_terracotta
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vision_card, parent, false)
        return VisionViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VisionViewHolder, position: Int) {
        holder.bind(items[position], colors[position % colors.size])
    }

    class VisionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvVisionTitle)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvVisionDescription)

        fun bind(card: VisionCard, colorRes: Int) {
            tvTitle.text = card.title
            tvDescription.text = card.description ?: ""
            itemView.background.setTint(itemView.context.getColor(colorRes))
        }
    }
}
