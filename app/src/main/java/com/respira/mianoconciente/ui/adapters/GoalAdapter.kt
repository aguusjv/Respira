package com.respira.mianoconciente.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.respira.mianoconciente.R
import com.respira.mianoconciente.data.local.Goal

class GoalAdapter(private val items: List<Goal>, private val onToggle: (Goal) -> Unit) :
    RecyclerView.Adapter<GoalAdapter.GoalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_goal, parent, false)
        return GoalViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class GoalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cbDone: CheckBox = itemView.findViewById(R.id.cbDone)
        private val tvDesc: TextView = itemView.findViewById(R.id.tvGoalDesc)
        private val tvCategory: TextView = itemView.findViewById(R.id.tvGoalCategory)

        fun bind(goal: Goal) {
            tvDesc.text = goal.description
            tvCategory.text = goal.category
            cbDone.isChecked = goal.isDone
            cbDone.setOnCheckedChangeListener { _, isChecked ->
                onToggle(goal.copy(isDone = isChecked))
            }
        }
    }
}
