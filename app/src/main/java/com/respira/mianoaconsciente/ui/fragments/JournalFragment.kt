package com.respira.mianoaconsciente.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.respira.mianoaconsciente.data.local.AppDatabase
import com.respira.mianoaconsciente.databinding.FragmentJournalBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class JournalFragment : Fragment() {

    private var _binding: FragmentJournalBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: DailyEntryAdapter
    private val database by lazy { AppDatabase.getInstance(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJournalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = DailyEntryAdapter(emptyList())
        binding.rvJournal.layoutManager = LinearLayoutManager(requireContext())
        binding.rvJournal.adapter = adapter
        loadEntries()
    }

    private fun loadEntries() {
        lifecycleScope.launch {
            val entries = withContext(Dispatchers.IO) {
                database.dailyEntryDao().getAll()
            }
            adapter.update(entries)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
