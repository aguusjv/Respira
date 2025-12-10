package com.respira.mianoaconsciente.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.respira.mianoaconsciente.R
import com.respira.mianoaconsciente.ui.main.MainActivity

class OnboardingActivity : AppCompatActivity() {

    private lateinit var pager: ViewPager2
    private lateinit var indicator: LinearLayout
    private lateinit var nextButton: Button

    private val pages = listOf(
        OnboardingPage(
            title = "Vision Board psicológico",
            body = "Esta app es una guía emocional para revisar tu año, intencionar el próximo y acompañarte día a día, desde la psicología y la amabilidad."
        ),
        OnboardingPage(
            title = "Qué vas a encontrar",
            body = "Ejercicios breves y prácticos",
            bullets = listOf(
                "Ejercicios de autoconocimiento",
                "Rueda de la vida emocional",
                "Intenciones y metas realistas",
                "Herramientas de regulación emocional"
            )
        ),
        OnboardingPage(
            title = "Cómo usar la app",
            body = "Avanzá a tu ritmo para cerrar tu año, planificar el próximo y volver cada día para registrar cómo estás y elegir una micro-acción."
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        val prefs = getSharedPreferences("onboarding", MODE_PRIVATE)
        if (prefs.getBoolean("seen", false)) {
            startMain()
            return
        }

        pager = findViewById(R.id.onboardingPager)
        indicator = findViewById(R.id.pagerIndicator)
        nextButton = findViewById(R.id.btnNext)

        pager.adapter = OnboardingAdapter(pages)
        updateIndicators(0)

        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateIndicators(position)
                nextButton.text = if (position == pages.lastIndex) getString(R.string.onboarding_start) else "Siguiente"
            }
        })

        nextButton.setOnClickListener {
            val current = pager.currentItem
            if (current == pages.lastIndex) {
                prefs.edit().putBoolean("seen", true).apply()
                startMain()
            } else {
                pager.currentItem = current + 1
            }
        }
    }

    private fun updateIndicators(position: Int) {
        indicator.removeAllViews()
        pages.indices.forEach { index ->
            val view = View(this).apply {
                layoutParams = LinearLayout.LayoutParams(16, 16).also {
                    it.marginEnd = 8
                }
                setBackgroundResource(
                    if (index == position) R.drawable.shape_indicator_active else R.drawable.shape_indicator_inactive
                )
            }
            indicator.addView(view)
        }
    }

    private fun startMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
