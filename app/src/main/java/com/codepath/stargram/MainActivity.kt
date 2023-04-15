package com.codepath.stargram

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.parse.ParseQuery
import com.parse.ParseUser

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

        findViewById<Button>(R.id.save_button).setOnClickListener {
            // send post to server without an image first
            val description = findViewById<EditText>(R.id.photo_comment_et).text.toString()
            val user = ParseUser.getCurrentUser()
            submitPost(description, user)
        }

        findViewById<Button>(R.id.take_picture_button).setOnClickListener {

        }


        // queryPosts()
    }

    private fun submitPost(description: String, user: ParseUser) {
        // Create Post object
        val post = Post()
        post.setDescription(description)
        post.setUser(user)
        post.saveInBackground { e ->
            if (e != null) {
                Log.e(tag, "error while saving post")
                e.printStackTrace()
                // TODO: show toast
            } else {
                Log.i(tag, "Successfully saved post")
                // TODO: reset editextfield to empty
                // TODO: reset the image view to empty
            }
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