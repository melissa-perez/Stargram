package com.codepath.stargram.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.stargram.Post
import com.codepath.stargram.PostAdapter
import com.codepath.stargram.R
import com.parse.ParseQuery

open class FeedFragment : Fragment() {
    lateinit var adapter: PostAdapter
    var allPosts: MutableList<Post> = mutableListOf()
    lateinit var rv: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // this is where we set up the views
        adapter = PostAdapter(requireContext(), allPosts)
        rv = view.findViewById<RecyclerView>(R.id.postRV)
        rv.adapter = adapter

        rv.layoutManager =
            LinearLayoutManager(requireContext())

        queryPosts()
    }

    companion object {
        const val TAG = "Feedfragment"
        fun newInstance(): FeedFragment {
            return FeedFragment()
        }
    }

    // query for all posts in server
    open fun queryPosts() {
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        // give us the user associated with post
        query.include(Post.KEY_USER)
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
                allPosts.clear()
                allPosts.addAll(posts)
                adapter.notifyDataSetChanged()
            }
        }
    }
}