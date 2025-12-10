package com.respira.mianoconciente.ui.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.respira.mianoconciente.R

data class OnboardingPage(
    val title: String,
    val body: String,
    val bulletPoints: List<String> = emptyList()
)

class OnboardingAdapter(private val pages: List<OnboardingPage>) :
    RecyclerView.Adapter<OnboardingAdapter.PageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_onboarding, parent, false)
        return PageViewHolder(view)
    }

    override fun getItemCount(): Int = pages.size

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        holder.bind(pages[position])
    }

    inner class PageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.tvTitle)
        private val body: TextView = itemView.findViewById(R.id.tvBody)
        private val listContainer: ViewGroup = itemView.findViewById(R.id.llList)

        fun bind(page: OnboardingPage) {
            title.text = page.title
            body.text = page.body
            listContainer.removeAllViews()
            if (page.bulletPoints.isNotEmpty()) {
                listContainer.visibility = View.VISIBLE
                val inflater = LayoutInflater.from(itemView.context)
                page.bulletPoints.forEach { point ->
                    val bullet = inflater.inflate(R.layout.simple_bullet_item, listContainer, false) as TextView
                    bullet.text = "• $point"
                    listContainer.addView(bullet)
                }
            } else {
                listContainer.visibility = View.GONE
            }
        }
    }
}
