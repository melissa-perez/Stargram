package com.codepath.stargram

import android.app.Application
import com.parse.Parse
import com.parse.ParseObject

private val CLIENT_KEY = BuildConfig.CLIENT_KEY
private val API_ID = BuildConfig.API_ID
private val SERVER_URL = BuildConfig.SERVER_URL

class StargramApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ParseObject.registerSubclass(Post::class.java)

        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG)

        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId(API_ID)
                .clientKey(CLIENT_KEY)
                .server(SERVER_URL).build()
        )

        // New test creation of object below
        //val testObject = ParseObject("TestObject")
        //testObject.put("foo", "bar")
        //testObject.saveInBackground()

        // push notification if needed here
    }
}