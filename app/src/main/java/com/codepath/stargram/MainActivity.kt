package com.codepath.stargram

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery

/**
 * Let user create a post by taking a photo with their camera
 */
class MainActivity : AppCompatActivity() {
    private val tag = "MainActivity/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //1. setting the description of post
        //2. a button to launch the camera
        //3. an imageview to show picture user has taken
        //4. a button to save and send the post to parse

        queryPosts()
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