package com.respira.mianoaconsciente.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.respira.mianoaconsciente.data.local.AppDatabase
import com.respira.mianoaconsciente.data.local.entities.VisionCard
import com.respira.mianoaconsciente.databinding.FragmentVisionBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VisionFragment : Fragment() {

    private var _binding: FragmentVisionBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: VisionCardAdapter
    private val database by lazy { AppDatabase.getInstance(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVisionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = VisionCardAdapter(emptyList())
        binding.rvVision.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvVision.adapter = adapter

        binding.btnAddVision.setOnClickListener { addCard() }
        loadCards()
    }

    private fun addCard() {
        val title = binding.etVisionTitle.text.toString().trim()
        val description = binding.etVisionDescription.text.toString().trim()
        if (title.isBlank() || description.isBlank()) {
            Toast.makeText(requireContext(), "Completá título y descripción", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                database.visionCardDao().insert(
                    VisionCard(title = title, description = description)
                )
            }
            binding.etVisionTitle.text?.clear()
            binding.etVisionDescription.text?.clear()
            loadCards()
        }
    }

    private fun loadCards() {
        lifecycleScope.launch {
            val cards = withContext(Dispatchers.IO) {
                database.visionCardDao().getAll()
            }
            adapter.update(cards)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
