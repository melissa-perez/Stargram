package com.codepath.stargram

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.parse.ParseUser

class LoginActivity : AppCompatActivity() {
    private val tag = "LoginActivity/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // check if user is logged in -> go to main activity instead
        if (ParseUser.getCurrentUser() != null) {
            goToMainActivity()
        }

        findViewById<Button>(R.id.login_button).setOnClickListener {
            val username: String = findViewById<EditText>(R.id.stargram_username).text.toString()
            val password: String = findViewById<EditText>(R.id.stargram_password).text.toString()

            loginUser(username, password)
        }

        findViewById<Button>(R.id.signup_button).setOnClickListener {
            val username: String = findViewById<EditText>(R.id.stargram_username).text.toString()
            val password: String = findViewById<EditText>(R.id.stargram_password).text.toString()

            signUpUser(username, password)
        }

    }

    private fun signUpUser(username: String, password: String) {
        val user = ParseUser()

        user.username = username
        user.setPassword(password)

        user.signUpInBackground { e ->
            if (e == null) {
                // Hooray! Let them use the app now.
                //TODO: user to main show a toast
                Log.i(tag, "Successfully signed up user")
                goToMainActivity()

            } else {
                e.printStackTrace()
                Toast.makeText(this, "Error sign up user", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun loginUser(username: String, password: String) {
        ParseUser.logInInBackground(
            username, password, ({ user, e ->
                if (user != null) {
                    Log.i(tag, "Successfully logged in user")
                    goToMainActivity()
                } else {
                    e.printStackTrace()
                    Toast.makeText(this, "Error login in user", Toast.LENGTH_SHORT).show()
                }
            })
        )
    }

    private fun goToMainActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
        // need a log out button
        //ParseUser.logOut()
        //val currentUser = ParseUser.getCurrentUser()
    }
}