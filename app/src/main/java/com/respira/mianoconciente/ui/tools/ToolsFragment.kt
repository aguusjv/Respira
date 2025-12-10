package com.respira.mianoconciente.ui.tools

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.core.animation.addListener
import com.respira.mianoconciente.R
import com.respira.mianoconciente.data.local.AppDatabase
import com.respira.mianoconciente.data.local.JournalEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ToolsFragment : Fragment() {

    private lateinit var breathCircle: View
    private lateinit var breathState: TextView
    private lateinit var journalInput: EditText
    private var animatorSet: AnimatorSet? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tools, container, false)
        breathCircle = view.findViewById(R.id.breathCircle)
        breathState = view.findViewById(R.id.tvBreathState)
        journalInput = view.findViewById(R.id.etJournal)

        view.findViewById<Button>(R.id.btnStartBreath).setOnClickListener { startBreathing() }
        view.findViewById<Button>(R.id.btnStopBreath).setOnClickListener { stopBreathing() }
        view.findViewById<Button>(R.id.btnSaveJournal).setOnClickListener { saveJournal() }
        return view
    }

    private fun startBreathing() {
        val inhale = ObjectAnimator.ofFloat(breathCircle, View.SCALE_X, 1f, 1.2f).setDuration(4000)
        val inhaleY = ObjectAnimator.ofFloat(breathCircle, View.SCALE_Y, 1f, 1.2f).setDuration(4000)
        val exhale = ObjectAnimator.ofFloat(breathCircle, View.SCALE_X, 1.2f, 1f).setDuration(6000)
        val exhaleY = ObjectAnimator.ofFloat(breathCircle, View.SCALE_Y, 1.2f, 1f).setDuration(6000)

        inhale.addUpdateListener { breathState.text = "Inhalá" }
        exhale.addUpdateListener { breathState.text = "Exhalá" }

        animatorSet = AnimatorSet().apply {
            playSequentially(inhale, inhaleY, exhale, exhaleY)
            start()
            addListener(onEnd = { startBreathing() })
        }
    }

    private fun stopBreathing() {
        animatorSet?.cancel()
        breathCircle.scaleX = 1f
        breathCircle.scaleY = 1f
        breathState.text = "Listo"
    }

    private fun saveJournal() {
        val text = journalInput.text.toString().ifBlank { return }
        lifecycleScope.launch(Dispatchers.IO) {
            AppDatabase.getInstance(requireContext()).journalEntryDao().insert(
                JournalEntry(date = System.currentTimeMillis(), text = text)
            )
            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), "Nota guardada", Toast.LENGTH_SHORT).show()
                journalInput.text?.clear()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopBreathing()
    }
}
