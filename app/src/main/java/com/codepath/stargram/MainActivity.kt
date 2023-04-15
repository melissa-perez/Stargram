package com.codepath.stargram

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.parse.ParseFile
import com.parse.ParseQuery
import com.parse.ParseUser
import java.io.File

/**
 * Let user create a post by taking a photo with their camera
 */
val APP_TAG = "MyCustomApp"
val CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034
val photoFileName = "photo.jpg"
var photoFile: File? = null

class MainActivity : AppCompatActivity() {
    private val tag = "MainActivity/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //1. setting the description of post
        //2. a button to launch the camera
        //3. an imageview to show picture user has taken
        //4. a button to save and send the post to parse
        // this version doesnt work correctly
        var cameraResultLauncher: ActivityResultLauncher<Intent>? = null


        findViewById<Button>(R.id.save_button).setOnClickListener {
            // send post to server without an image first
            val description = findViewById<EditText>(R.id.photo_comment_et).text.toString()
            val user = ParseUser.getCurrentUser()
            if (photoFile != null) {
                submitPost(description, user, photoFile!!)
            } else {
                // TODO: print error or toast
            }
        }

        findViewById<Button>(R.id.take_picture_button).setOnClickListener {
            onLaunchCamera()
        }


        // queryPosts()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            // by this point we have the camera photo on disk
            val takenImage = BitmapFactory.decodeFile(photoFile!!.absolutePath)
            // RESIZE BITMAP, see section below
            // Load the taken image into a preview
            val ivPreview = findViewById<ImageView>(R.id.photo_to_upload)
            Log.i(tag, takenImage.toString())
            ivPreview.setImageBitmap(takenImage)
            ivPreview!!.setImageBitmap(takenImage)
        } else { // Result was a failure
            Toast.makeText(this, "Error taking picture", Toast.LENGTH_SHORT).show()
        }

    }

    fun onLaunchCamera() {
        // create Intent to take a picture and return control to the calling application
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName)

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        if (photoFile != null) {
            val fileProvider: Uri =
                FileProvider.getUriForFile(this, "com.codepath.fileprovider", photoFile!!)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)

            // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
            // So as long as the result is not null, it's safe to use the intent.

            // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
            // So as long as the result is not null, it's safe to use the intent.
            if (intent.resolveActivity(packageManager) != null) {
                // Start the image capture intent to take photo
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
            }
        }
    }

    // Returns the File for a photo stored on disk given the fileName
    fun getPhotoFileUri(fileName: String): File {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        val mediaStorageDir =
            File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG)

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(APP_TAG, "failed to create directory")
        }

        // Return the file target for the photo based on filename
        return File(mediaStorageDir.path + File.separator + fileName)
    }

    private fun submitPost(description: String, user: ParseUser, file: File) {
        // Create Post object
        val post = Post()
        post.setDescription(description)
        post.setUser(user)
        post.setImage(ParseFile(file))
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