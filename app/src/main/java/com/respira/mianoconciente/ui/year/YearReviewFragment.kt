package com.respira.mianoconciente.ui.year

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.respira.mianoconciente.R
import com.respira.mianoconciente.data.local.AppDatabase
import com.respira.mianoconciente.data.local.EmotionReflection
import com.respira.mianoconciente.data.local.InnerResources
import com.respira.mianoconciente.data.local.LifeWheel
import com.respira.mianoconciente.data.local.TimelineEvent
import com.respira.mianoconciente.ui.adapters.TimelineEventAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class YearReviewFragment : Fragment() {

    private lateinit var etEventTitle: EditText
    private lateinit var etEventDescription: EditText
    private lateinit var etEventEmotion: EditText
    private lateinit var rvEvents: RecyclerView
    private lateinit var chipContainer: LinearLayout
    private lateinit var etVersionLeave: EditText
    private lateinit var tvWheelSummary: TextView
    private lateinit var etHeldMe: EditText
    private lateinit var etQuality: EditText
    private lateinit var etDiscovery: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_year_review, container, false)
        etEventTitle = view.findViewById(R.id.etEventTitle)
        etEventDescription = view.findViewById(R.id.etEventDescription)
        etEventEmotion = view.findViewById(R.id.etEventEmotion)
        rvEvents = view.findViewById(R.id.rvEvents)
        chipContainer = view.findViewById(R.id.chipContainer)
        etVersionLeave = view.findViewById(R.id.etVersionLeave)
        tvWheelSummary = view.findViewById(R.id.tvWheelSummary)
        etHeldMe = view.findViewById(R.id.etHeldMe)
        etQuality = view.findViewById(R.id.etQuality)
        etDiscovery = view.findViewById(R.id.etDiscovery)

        rvEvents.layoutManager = LinearLayoutManager(requireContext())

        val emotions = listOf("Ansiedad", "Calma", "Curiosidad", "Tristeza", "Alegría", "Estrés", "Gratitud", "Confusión", "Otro")
        emotions.forEach { emotion ->
            val cb = CheckBox(requireContext())
            cb.text = emotion
            cb.setTextColor(resources.getColor(R.color.text_brown, null))
            chipContainer.addView(cb)
        }

        view.findViewById<Button>(R.id.btnAddEvent).setOnClickListener { saveEvent() }
        view.findViewById<Button>(R.id.btnSaveEmotions).setOnClickListener { saveEmotions() }
        view.findViewById<Button>(R.id.btnSaveWheel).setOnClickListener { saveWheel(view) }
        view.findViewById<Button>(R.id.btnSaveResources).setOnClickListener { saveResources() }

        loadEvents()
        loadWheelSummary()
        loadEmotionReflection()
        return view
    }

    private fun saveEvent() {
        val title = etEventTitle.text.toString().ifBlank { return }
        val desc = etEventDescription.text.toString().ifBlank { null }
        val emotion = etEventEmotion.text.toString().ifBlank { "" }
        lifecycleScope.launch(Dispatchers.IO) {
            val dao = AppDatabase.getInstance(requireContext()).timelineEventDao()
            dao.insert(
                TimelineEvent(
                    date = System.currentTimeMillis(),
                    title = title,
                    description = desc,
                    mainEmotion = emotion
                )
            )
            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), "Evento agregado", Toast.LENGTH_SHORT).show()
                etEventTitle.text?.clear()
                etEventDescription.text?.clear()
                etEventEmotion.text?.clear()
                loadEvents()
            }
        }
    }

    private fun loadEvents() {
        lifecycleScope.launch(Dispatchers.IO) {
            val events = AppDatabase.getInstance(requireContext()).timelineEventDao().getAll()
            withContext(Dispatchers.Main) {
                rvEvents.adapter = TimelineEventAdapter(events)
            }
        }
    }

    private fun saveEmotions() {
        val selected = (0 until chipContainer.childCount)
            .map { chipContainer.getChildAt(it) as CheckBox }
            .filter { it.isChecked }
            .joinToString(", ") { it.text }
        val versionLeave = etVersionLeave.text.toString().ifBlank { null }

        lifecycleScope.launch(Dispatchers.IO) {
            AppDatabase.getInstance(requireContext()).emotionReflectionDao().insert(
                EmotionReflection(
                    frequentEmotions = selected.ifBlank { null },
                    versionToLeave = versionLeave
                )
            )
            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), "Reflexión guardada", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveWheel(root: View) {
        val health = root.findViewById<SeekBar>(R.id.seekHealth).progress + 1
        val money = root.findViewById<SeekBar>(R.id.seekMoney).progress + 1
        val personal = root.findViewById<SeekBar>(R.id.seekPersonal).progress + 1
        val spiritual = root.findViewById<SeekBar>(R.id.seekSpiritual).progress + 1
        val social = root.findViewById<SeekBar>(R.id.seekSocial).progress + 1
        val family = root.findViewById<SeekBar>(R.id.seekFamily).progress + 1
        val leisure = root.findViewById<SeekBar>(R.id.seekLeisure).progress + 1
        val work = root.findViewById<SeekBar>(R.id.seekWork).progress + 1

        lifecycleScope.launch(Dispatchers.IO) {
            AppDatabase.getInstance(requireContext()).lifeWheelDao().insert(
                LifeWheel(
                    date = System.currentTimeMillis(),
                    health = health,
                    money = money,
                    personalGrowth = personal,
                    spirituality = spiritual,
                    social = social,
                    familyLove = family,
                    leisure = leisure,
                    work = work
                )
            )
            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), "Rueda guardada", Toast.LENGTH_SHORT).show()
                tvWheelSummary.text = getString(
                    R.string.wheel_summary_format,
                    health,
                    money,
                    personal,
                    spiritual,
                    social,
                    family,
                    leisure,
                    work
                )
            }
        }
    }

    private fun loadWheelSummary() {
        lifecycleScope.launch(Dispatchers.IO) {
            val wheel = AppDatabase.getInstance(requireContext()).lifeWheelDao().getAll().firstOrNull()
            withContext(Dispatchers.Main) {
                wheel?.let {
                    tvWheelSummary.text = getString(
                        R.string.wheel_summary_format,
                        it.health,
                        it.money,
                        it.personalGrowth,
                        it.spirituality,
                        it.social,
                        it.familyLove,
                        it.leisure,
                        it.work
                    )
                }
            }
        }
    }

    private fun loadEmotionReflection() {
        lifecycleScope.launch(Dispatchers.IO) {
            val reflection = AppDatabase.getInstance(requireContext()).emotionReflectionDao().getLatest()
            val resources = AppDatabase.getInstance(requireContext()).innerResourcesDao().getLatest()
            withContext(Dispatchers.Main) {
                reflection?.let {
                    etVersionLeave.setText(it.versionToLeave ?: "")
                }
                resources?.let {
                    etHeldMe.setText(it.heldMe ?: "")
                    etQuality.setText(it.qualityNeeded ?: "")
                    etDiscovery.setText(it.discovery ?: "")
                }
            }
        }
    }

    private fun saveResources() {
        val held = etHeldMe.text.toString().ifBlank { null }
        val quality = etQuality.text.toString().ifBlank { null }
        val discovery = etDiscovery.text.toString().ifBlank { null }

        lifecycleScope.launch(Dispatchers.IO) {
            AppDatabase.getInstance(requireContext()).innerResourcesDao().insert(
                InnerResources(
                    heldMe = held,
                    qualityNeeded = quality,
                    discovery = discovery
                )
            )
            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), "Guardado", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
