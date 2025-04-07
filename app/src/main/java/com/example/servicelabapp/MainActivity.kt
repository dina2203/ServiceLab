package com.example.servicelabapp

import android.content.*
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var characterEditText: EditText
    private lateinit var serviceIntent: Intent

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val char = intent?.getCharExtra("randomCharacter", '?') ?: '?'
            characterEditText.setText(char.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        characterEditText = findViewById(R.id.editText_random)
        serviceIntent = Intent(this, RandomCharacterService::class.java)
    }

    fun startService(view: View) {
        startService(serviceIntent)
    }

    fun stopService(view: View) {
        stopService(serviceIntent)
        characterEditText.setText("")
    }

    fun goToForeground(view: View) {
        startActivity(Intent(this, ForegroundActivity::class.java))
    }

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter("my.custom.action.tag.lab6")
        ContextCompat.registerReceiver(this, receiver, filter, ContextCompat.RECEIVER_NOT_EXPORTED)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }
}