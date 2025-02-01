package com.example.fitnessapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.fitnessapp.R
import com.example.fitnessapp.manager.Session

class Main : AppCompatActivity() {
    private lateinit var sessionManager: Session

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sessionManager = Session(this)

        val alreadyHaveAccountButton = findViewById<Button>(R.id.already_have_account_button)
        alreadyHaveAccountButton.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
        val registerButton: Button = findViewById(R.id.register_button)
        registerButton.setOnClickListener {
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
        }
    }
}