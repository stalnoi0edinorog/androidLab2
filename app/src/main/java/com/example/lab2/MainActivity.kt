package com.example.lab2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val TAG = "Life_Cycle"
    private var check = true
    var secondsElapsed: Int = 0

    var backgroundThread = Thread {
        while (true) {
            Thread.sleep(1000)
            if (check)
                textSecondsElapsed.post {
                    textSecondsElapsed.setText("Seconds elapsed: " + secondsElapsed++)
                }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        backgroundThread.start()
        Log.d(TAG, "Activity created")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "Activity restarted")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "Activity became visible")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        secondsElapsed = savedInstanceState.getInt("seconds")
        super.onRestoreInstanceState(savedInstanceState)
        Log.d(TAG, "Data restored")
    }

    override fun onResume() {
        super.onResume()
        check = true
        Log.d(TAG, "Activity resumed")
    }

    override fun onPause() {
        super.onPause()
        check = false
        Log.d(TAG, "Activity paused")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "Activity stopped")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("seconds", secondsElapsed)
        super.onSaveInstanceState(outState)
        Log.d(TAG, "Data saved")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Activity destroyed")
    }

}