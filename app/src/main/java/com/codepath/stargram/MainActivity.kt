package com.codepath.stargram

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.codepath.stargram.fragments.ComposeFragment
import com.codepath.stargram.fragments.FeedFragment
import com.codepath.stargram.fragments.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val tag = "MainActivity/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragmentManager: FragmentManager = supportFragmentManager
        val homeFragment: Fragment = HomeFragment()
        val composeFragment: Fragment = ComposeFragment()
        val feedFragment: Fragment = FeedFragment()

        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener { item ->
            lateinit var fragment: Fragment
            when (item.itemId) {
                R.id.item_home -> {
                    fragment = homeFragment
                }
                R.id.item_compose -> {
                    fragment = composeFragment
                }
                R.id.item_profile -> {
                    fragment = feedFragment
                }
            }
            fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit()
            true
        }
        findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId =
            R.id.item_compose
    }

}