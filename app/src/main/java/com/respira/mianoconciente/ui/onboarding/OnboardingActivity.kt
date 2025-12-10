package com.respira.mianoconciente.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.respira.mianoconciente.R
import com.respira.mianoconciente.ui.MainActivity
import com.respira.mianoconciente.utils.Prefs

class OnboardingActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var btnNext: Button
    private lateinit var indicator: LinearLayout
    private lateinit var prefs: Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = Prefs(this)
        if (prefs.hasSeenOnboarding()) {
            goToMain()
            return
        }
        setContentView(R.layout.activity_onboarding)

        viewPager = findViewById(R.id.onboardingPager)
        btnNext = findViewById(R.id.btnNext)
        indicator = findViewById(R.id.pagerIndicator)

        val pages = listOf(
            OnboardingPage(
                title = "Vision Board psicológico",
                body = "Esta app es una guía emocional para revisar tu año, intencionar el próximo y acompañarte día a día, desde la psicología y la amabilidad."
            ),
            OnboardingPage(
                title = "Qué vas a encontrar",
                body = "",
                bulletPoints = listOf(
                    "Ejercicios de autoconocimiento.",
                    "Rueda de la vida emocional.",
                    "Intenciones y metas realistas.",
                    "Herramientas de regulación emocional (respiración, journaling)."
                )
            ),
            OnboardingPage(
                title = "Cómo usar la app",
                body = "Podés avanzar a tu ritmo. Usarla para cerrar tu año, planificar el próximo y volver cada día para registrar cómo estás y elegir una micro-acción de autocuidado."
            )
        )

        viewPager.adapter = OnboardingAdapter(pages)
        setupIndicators(pages.size)
        updateButtonText(0)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                selectIndicator(position)
                updateButtonText(position)
            }
        })

        btnNext.setOnClickListener {
            val current = viewPager.currentItem
            if (current < pages.lastIndex) {
                viewPager.currentItem = current + 1
            } else {
                prefs.setOnboardingSeen(true)
                goToMain()
            }
        }
    }

    private fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun setupIndicators(count: Int) {
        indicator.removeAllViews()
        repeat(count) {
            val dot = ImageView(this).apply {
                setImageDrawable(ContextCompat.getDrawable(this@OnboardingActivity, R.drawable.shape_indicator_inactive))
                val params = LinearLayout.LayoutParams(24, 24)
                params.marginEnd = 8
                layoutParams = params
            }
            indicator.addView(dot)
        }
        selectIndicator(0)
    }

    private fun selectIndicator(index: Int) {
        for (i in 0 until indicator.childCount) {
            val dot = indicator.getChildAt(i) as ImageView
            val drawable = if (i == index) R.drawable.shape_indicator_active else R.drawable.shape_indicator_inactive
            dot.setImageDrawable(ContextCompat.getDrawable(this, drawable))
        }
    }

    private fun updateButtonText(position: Int) {
        btnNext.text = if (position == 2) getString(R.string.onboarding_start) else "Siguiente"
    }
}
