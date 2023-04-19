package com.codepath.stargram.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codepath.stargram.Post
import com.codepath.stargram.R
import com.parse.ParseQuery
import com.parse.ParseUser

class HomeFragment : FeedFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun queryPosts() {
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        // give us the user associated with post
        query.include(Post.KEY_USER)
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser())
        query.addDescendingOrder("createdAt")
        // find all posts in parse
        query.findInBackground { posts, e ->
            if (e != null) {
                Log.i(TAG, "ERROR fetching posts")
            } else {
                if (posts != null) {
                    for (post in posts) {
                        Log.i(
                            TAG, "POST: ${post.getDescription()}, USER: ${post.getUser()?.username}"
                        )
                    }
                }
                allPosts.addAll(posts)
                adapter.notifyDataSetChanged()
            }
        }
    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}