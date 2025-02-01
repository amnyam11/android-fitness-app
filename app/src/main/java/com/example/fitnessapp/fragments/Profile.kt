package com.example.fitnessapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.fitnessapp.R
import com.example.fitnessapp.activities.ChangePassword
import com.example.fitnessapp.activities.Login
import com.example.fitnessapp.db.UserDatabase
import com.example.fitnessapp.manager.Session
import com.example.fitnessapp.db.dataAccessObject.User
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Profile : Fragment() {

    private lateinit var sessionManager: Session
    private lateinit var userDao: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        sessionManager = Session(requireContext())
        userDao = UserDatabase.getDatabase(requireContext()).userDao()

        val loginEditText = view.findViewById<TextInputEditText>(R.id.login_edittext)
        val usernameEditText = view.findViewById<TextInputEditText>(R.id.username_edittext)
        val changePasswordButton = view.findViewById<Button>(R.id.change_password_button)
        val logoutButton = view.findViewById<Button>(R.id.logout_button)

        changePasswordButton.setOnClickListener {
            val intent = Intent(activity, ChangePassword::class.java)
            startActivity(intent)
        }

        logoutButton.setOnClickListener {
            sessionManager.logout()
            val intent = Intent(activity, Login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        loginEditText.setText(sessionManager.getUserLogin())

        lifecycleScope.launch {
            val nickname = fetchUsernameFromDatabase()
            withContext(Dispatchers.Main) {
                usernameEditText.setText(nickname)
            }
        }

        return view
    }

    private suspend fun fetchUsernameFromDatabase(): String? {
        val login = sessionManager.getUserLogin()
        return withContext(Dispatchers.IO) {
            login?.let {
                val user = userDao.findUserByLogin(it)
                user?.username
            }
        }
    }
}