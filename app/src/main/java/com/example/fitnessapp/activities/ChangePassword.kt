package com.example.fitnessapp.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.fitnessapp.R
import com.example.fitnessapp.db.UserDatabase
import com.example.fitnessapp.manager.Session
import com.example.fitnessapp.db.dataAccessObject.User
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChangePassword : AppCompatActivity() {

    private lateinit var sessionManager: Session
    private lateinit var userDao: User


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        sessionManager = Session(this)
        userDao = UserDatabase.getDatabase(this).userDao()


        val oldPasswordEditText = findViewById<TextInputEditText>(R.id.old_password_edittext)
        val newPasswordEditText = findViewById<TextInputEditText>(R.id.new_password_edittext)
        val repeatNewPasswordEditText = findViewById<TextInputEditText>(R.id.repeat_new_password_edittext)
        val agreeChangesButton = findViewById<MaterialButton>(R.id.agree_changes_button)

        agreeChangesButton.setOnClickListener {
            val oldPassword = oldPasswordEditText.text.toString()
            val newPassword = newPasswordEditText.text.toString()
            val repeatNewPassword = repeatNewPasswordEditText.text.toString()

            if (newPassword == repeatNewPassword) {

                lifecycleScope.launch {
                    val login = sessionManager.getUserLogin()
                    val user = login?.let { userDao.findUserByLoginAndPassword(it, oldPassword) }
                    if (user != null) {
                        updatePassword(login, newPassword)
                        Toast.makeText(this@ChangePassword, "Пароль изменён", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@ChangePassword, "Неверный пароль", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private suspend fun updatePassword(login: String, newPassword: String) {
        withContext(Dispatchers.IO) {
            userDao.updatePassword(login, newPassword)
        }
    }
}