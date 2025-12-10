package com.respira.mianoaconsciente.ui.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.respira.mianoaconsciente.R

data class OnboardingPage(
    val title: String,
    val body: String,
    val bullets: List<String> = emptyList()
)

class OnboardingAdapter(private val items: List<OnboardingPage>) :
    RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_onboarding, parent, false)
        return OnboardingViewHolder(view)
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class OnboardingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.tvTitle)
        private val body: TextView = itemView.findViewById(R.id.tvBody)
        private val list: LinearLayout = itemView.findViewById(R.id.llList)

        fun bind(page: OnboardingPage) {
            title.text = page.title
            body.text = page.body
            list.removeAllViews()
            val inflater = LayoutInflater.from(itemView.context)
            page.bullets.forEach { text ->
                val bullet = inflater.inflate(R.layout.simple_bullet_item, list, false) as TextView
                bullet.text = "• $text"
                list.addView(bullet)
            }
        }
    }
}
