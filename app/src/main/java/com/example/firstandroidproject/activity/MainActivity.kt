package com.example.firstandroidproject.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.firstandroidproject.R

class MainActivity : AppCompatActivity() {

    private val mainTag = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(mainTag, "OnCreate")

        val btnStart = findViewById<Button>(R.id.btnStart)

        btnStart.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(mainTag, "OnStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(mainTag, "OnResume")
    }
    override fun onPause() {
        super.onPause()
        Log.d(mainTag, "OnPause")
    }
    override fun onStop() {
        super.onStop()
        Log.d(mainTag, "OnStop")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(mainTag, "OnDestroy")
    }
    override fun onRestart() {
        super.onRestart()
        Log.d(mainTag, "OnRestart")
    }
}