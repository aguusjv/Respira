package com.respira.mianoaconsciente.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.respira.mianoaconsciente.R
import com.respira.mianoaconsciente.ui.fragments.HomeFragment
import com.respira.mianoaconsciente.ui.fragments.JournalFragment
import com.respira.mianoaconsciente.ui.fragments.ToolsFragment
import com.respira.mianoaconsciente.ui.fragments.VisionFragment

class MainActivity : AppCompatActivity() {

    private val homeFragment = HomeFragment()
    private val journalFragment = JournalFragment()
    private val visionFragment = VisionFragment()
    private val toolsFragment = ToolsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> switchFragment(homeFragment)
                R.id.nav_journal -> switchFragment(journalFragment)
                R.id.nav_vision -> switchFragment(visionFragment)
                R.id.nav_tools -> switchFragment(toolsFragment)
            }
            true
        }

        if (savedInstanceState == null) {
            bottomNavigation.selectedItemId = R.id.nav_home
        }
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
