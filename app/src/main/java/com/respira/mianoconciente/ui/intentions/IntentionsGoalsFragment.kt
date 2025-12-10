package com.respira.mianoconciente.ui.intentions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.respira.mianoconciente.R
import com.respira.mianoconciente.data.local.AppDatabase
import com.respira.mianoconciente.data.local.Goal
import com.respira.mianoconciente.data.local.VisionCard
import com.respira.mianoconciente.data.local.YearIntentions
import com.respira.mianoconciente.ui.adapters.GoalAdapter
import com.respira.mianoconciente.ui.adapters.VisionCardAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IntentionsGoalsFragment : Fragment() {

    private lateinit var etGuideWord: EditText
    private lateinit var etGuideReason: EditText
    private lateinit var etEmotions: EditText
    private lateinit var etDreams: EditText
    private lateinit var etAreas: EditText
    private lateinit var etStop: EditText
    private lateinit var etCultivate: EditText
    private lateinit var etLearn: EditText
    private lateinit var spCategory: Spinner
    private lateinit var etGoalDescription: EditText
    private lateinit var rvGoals: RecyclerView
    private lateinit var rvVision: RecyclerView
    private lateinit var etVisionTitle: EditText
    private lateinit var etVisionDescription: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_intentions_goals, container, false)
        etGuideWord = view.findViewById(R.id.etGuideWord)
        etGuideReason = view.findViewById(R.id.etGuideReason)
        etEmotions = view.findViewById(R.id.etEmotions)
        etDreams = view.findViewById(R.id.etDreams)
        etAreas = view.findViewById(R.id.etAreas)
        etStop = view.findViewById(R.id.etStop)
        etCultivate = view.findViewById(R.id.etCultivate)
        etLearn = view.findViewById(R.id.etLearn)
        spCategory = view.findViewById(R.id.spCategory)
        etGoalDescription = view.findViewById(R.id.etGoalDescription)
        rvGoals = view.findViewById(R.id.rvGoals)
        rvVision = view.findViewById(R.id.rvVision)
        etVisionTitle = view.findViewById(R.id.etVisionTitle)
        etVisionDescription = view.findViewById(R.id.etVisionDescription)

        rvGoals.layoutManager = LinearLayoutManager(requireContext())
        rvVision.layoutManager = GridLayoutManager(requireContext(), 2)

        view.findViewById<Button>(R.id.btnSaveIntentions).setOnClickListener { saveIntentions() }
        view.findViewById<Button>(R.id.btnAddGoal).setOnClickListener { saveGoal() }
        view.findViewById<Button>(R.id.btnAddVision).setOnClickListener { saveVisionCard() }

        loadIntentions()
        loadGoals()
        loadVisionCards()
        return view
    }

    private fun saveIntentions() {
        val intentions = YearIntentions(
            emotionsToFeel = etEmotions.text.toString().ifBlank { null },
            dreams = etDreams.text.toString().ifBlank { null },
            areasToImprove = etAreas.text.toString().ifBlank { null },
            stopNormalizing = etStop.text.toString().ifBlank { null },
            cultivate = etCultivate.text.toString().ifBlank { null },
            emotionalLearning = etLearn.text.toString().ifBlank { null },
            guideWord = etGuideWord.text.toString().ifBlank { null },
            guideWordReason = etGuideReason.text.toString().ifBlank { null }
        )

        lifecycleScope.launch(Dispatchers.IO) {
            AppDatabase.getInstance(requireContext()).yearIntentionsDao().insert(intentions)
            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), "Intenciones guardadas", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveGoal() {
        val description = etGoalDescription.text.toString().ifBlank { return }
        val category = spCategory.selectedItem.toString()
        lifecycleScope.launch(Dispatchers.IO) {
            val dao = AppDatabase.getInstance(requireContext()).goalDao()
            dao.insert(Goal(category = category, description = description, isDone = false))
            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), "Meta agregada", Toast.LENGTH_SHORT).show()
                etGoalDescription.text?.clear()
                loadGoals()
            }
        }
    }

    private fun saveVisionCard() {
        val title = etVisionTitle.text.toString().ifBlank { return }
        val description = etVisionDescription.text.toString().ifBlank { null }
        lifecycleScope.launch(Dispatchers.IO) {
            AppDatabase.getInstance(requireContext()).visionCardDao().insert(
                VisionCard(title = title, description = description)
            )
            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), "Tarjeta agregada", Toast.LENGTH_SHORT).show()
                etVisionTitle.text?.clear()
                etVisionDescription.text?.clear()
                loadVisionCards()
            }
        }
    }

    private fun loadIntentions() {
        lifecycleScope.launch(Dispatchers.IO) {
            val latest = AppDatabase.getInstance(requireContext()).yearIntentionsDao().getLatest()
            withContext(Dispatchers.Main) {
                latest?.let {
                    etEmotions.setText(it.emotionsToFeel ?: "")
                    etDreams.setText(it.dreams ?: "")
                    etAreas.setText(it.areasToImprove ?: "")
                    etStop.setText(it.stopNormalizing ?: "")
                    etCultivate.setText(it.cultivate ?: "")
                    etLearn.setText(it.emotionalLearning ?: "")
                    etGuideWord.setText(it.guideWord ?: "")
                    etGuideReason.setText(it.guideWordReason ?: "")
                }
            }
        }
    }

    private fun loadGoals() {
        lifecycleScope.launch(Dispatchers.IO) {
            val dao = AppDatabase.getInstance(requireContext()).goalDao()
            val categories = resources.getStringArray(R.array.goal_categories)
            val allGoals = categories.flatMap { category ->
                dao.getByCategory(category).map { it.copy(category = category) }
            }
            withContext(Dispatchers.Main) {
                rvGoals.adapter = GoalAdapter(allGoals) { updated ->
                    lifecycleScope.launch(Dispatchers.IO) {
                        dao.insert(updated)
                    }
                }
            }
        }
    }

    private fun loadVisionCards() {
        lifecycleScope.launch(Dispatchers.IO) {
            val cards = AppDatabase.getInstance(requireContext()).visionCardDao().getAll()
            withContext(Dispatchers.Main) {
                rvVision.adapter = VisionCardAdapter(cards)
            }
        }
    }
}
