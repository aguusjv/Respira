package com.respira.mianoconciente.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.respira.mianoconciente.R
import com.respira.mianoconciente.data.local.AppDatabase
import com.respira.mianoconciente.ui.adapters.DailyEntryAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TodayHistoryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_today_history, container, false)
        recyclerView = view.findViewById(R.id.rvHistory)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        loadEntries()
        return view
    }

    private fun loadEntries() {
        lifecycleScope.launch(Dispatchers.IO) {
            val entries = AppDatabase.getInstance(requireContext()).dailyEntryDao().getAll()
            withContext(Dispatchers.Main) {
                recyclerView.adapter = DailyEntryAdapter(entries)
            }
        }
    }
}
