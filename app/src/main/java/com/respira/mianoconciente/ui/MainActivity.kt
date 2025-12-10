package com.respira.mianoconciente.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.respira.mianoconciente.R
import com.respira.mianoconciente.ui.home.TodayFragment
import com.respira.mianoconciente.ui.intentions.IntentionsGoalsFragment
import com.respira.mianoconciente.ui.tools.ToolsFragment
import com.respira.mianoconciente.ui.year.YearReviewFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_today -> switchFragment(TodayFragment())
                R.id.nav_year -> switchFragment(YearReviewFragment())
                R.id.nav_intentions -> switchFragment(IntentionsGoalsFragment())
                R.id.nav_tools -> switchFragment(ToolsFragment())
            }
            true
        }

        if (savedInstanceState == null) {
            bottomNav.selectedItemId = R.id.nav_today
        }
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
