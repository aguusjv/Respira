package com.respira.mianoaconsciente.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.respira.mianoaconsciente.data.local.AppDatabase
import com.respira.mianoaconsciente.data.local.entities.DailyEntry
import com.respira.mianoaconsciente.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: DailyEntryAdapter
    private val database by lazy { AppDatabase.getInstance(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = DailyEntryAdapter(emptyList())
        binding.rvEntries.layoutManager = LinearLayoutManager(requireContext())
        binding.rvEntries.adapter = adapter

        binding.btnSave.setOnClickListener { saveEntry() }
        loadEntries()
    }

    private fun saveEntry() {
        val selectedId = binding.rgMood.checkedRadioButtonId
        if (selectedId == -1) {
            Toast.makeText(requireContext(), "Elegí un estado", Toast.LENGTH_SHORT).show()
            return
        }
        val moodValue = binding.root.findViewById<RadioButton>(selectedId).text.toString().toInt()
        val noteText = binding.etNote.text.toString().ifBlank { null }

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                database.dailyEntryDao().insert(
                    DailyEntry(
                        date = System.currentTimeMillis(),
                        mood = moodValue,
                        note = noteText
                    )
                )
            }
            binding.etNote.text?.clear()
            binding.rgMood.clearCheck()
            Toast.makeText(requireContext(), "Guardado", Toast.LENGTH_SHORT).show()
            loadEntries()
        }
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
