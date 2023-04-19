package com.codepath.stargram

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.codepath.stargram.fragments.ComposeFragment
import com.codepath.stargram.fragments.FeedFragment
import com.codepath.stargram.fragments.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.parse.ParseQuery

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
        findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId = R.id.item_compose
        // queryPosts()
    }


    // query for all posts in server
    private fun queryPosts() {
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        // give us the user associated with post
        query.include(Post.KEY_USER)
        // find all posts in parse
        query.findInBackground { posts, e ->
            if (e != null) {
                Log.i(tag, "ERROR fetching posts")
            } else {
                if (posts != null) {
                    for (post in posts) {
                        Log.i(
                            tag,
                            "POST: ${post.getDescription()}, USER: ${post.getUser()?.username}"
                        )
                    }
                }
            }
        }
    }

}