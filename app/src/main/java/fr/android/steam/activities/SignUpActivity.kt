package fr.android.steam.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import fr.android.steam.R

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        findViewById<AppCompatButton>(R.id.signup_confirm_btn).setOnClickListener {
            val username = findViewById<TextView>(R.id.signup_username_input).text.toString()
            val email = findViewById<TextView>(R.id.signup_email_input).text.toString()
            val password = findViewById<TextView>(R.id.signup_password_input).text.toString()
            signUp(username, email, password)
        }

        findViewById<AppCompatButton>(R.id.signup_goto_signin_btn).setOnClickListener {
            startActivity(Intent(applicationContext, SignInActivity::class.java))
            finish()
        }
    }

    fun signUp(username: String, email: String, password: String) {
        Toast.makeText(
        this@SignUpActivity,
        "Welcome $username, please login",
        Toast.LENGTH_SHORT).show()

        startActivity(Intent(applicationContext, SignInActivity::class.java))
        finish()
    }
}