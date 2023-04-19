package com.codepath.stargram.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.codepath.stargram.Post
import com.codepath.stargram.R
import com.parse.ParseQuery

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FeedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FeedFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // this is where we set up the views
        view.findViewById<RecyclerView>(R.id.postRV)

        queryPosts()
    }

    companion object {
        const val TAG = "Feedfragment"
        fun newInstance(): FeedFragment {
            return FeedFragment()
        }
    }

    // query for all posts in server
    private fun queryPosts() {
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        // give us the user associated with post
        query.include(Post.KEY_USER)
        // find all posts in parse
        query.findInBackground { posts, e ->
            if (e != null) {
                Log.i(TAG, "ERROR fetching posts")
            } else {
                if (posts != null) {
                    for (post in posts) {
                        Log.i(
                            TAG,
                            "POST: ${post.getDescription()}, USER: ${post.getUser()?.username}"
                        )
                    }
                }
            }
        }
    }
}