package com.respira.mianoconciente.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.respira.mianoconciente.R
import com.respira.mianoconciente.data.local.AppDatabase
import com.respira.mianoconciente.data.local.DailyEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TodayFragment : Fragment() {

    private lateinit var moodGroup: RadioGroup
    private lateinit var noteInput: EditText
    private lateinit var microActionInput: EditText
    private lateinit var saveButton: Button
    private lateinit var historyButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_today, container, false)
        moodGroup = view.findViewById(R.id.rgMood)
        noteInput = view.findViewById(R.id.etNote)
        microActionInput = view.findViewById(R.id.etMicroAction)
        saveButton = view.findViewById(R.id.btnSave)
        historyButton = view.findViewById(R.id.btnHistory)

        saveButton.setOnClickListener { saveEntry() }
        historyButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, TodayHistoryFragment())
                .addToBackStack(null)
                .commit()
        }
        return view
    }

    private fun saveEntry() {
        val mood = when (moodGroup.checkedRadioButtonId) {
            R.id.mood1 -> 1
            R.id.mood2 -> 2
            R.id.mood3 -> 3
            R.id.mood4 -> 4
            R.id.mood5 -> 5
            else -> 0
        }
        if (mood == 0) {
            Toast.makeText(requireContext(), "Elegí un estado emocional", Toast.LENGTH_SHORT).show()
            return
        }
        val note = noteInput.text.toString().ifBlank { null }
        val micro = microActionInput.text.toString().ifBlank { null }

        lifecycleScope.launch(Dispatchers.IO) {
            val dao = AppDatabase.getInstance(requireContext()).dailyEntryDao()
            dao.insert(
                DailyEntry(
                    date = System.currentTimeMillis(),
                    mood = mood,
                    note = note,
                    microAction = micro
                )
            )
            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), "Gracias por darte este momento.", Toast.LENGTH_LONG).show()
                noteInput.text?.clear()
                microActionInput.text?.clear()
                moodGroup.clearCheck()
            }
        }
    }
}
