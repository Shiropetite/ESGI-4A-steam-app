package fr.android.steam.activities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import fr.android.steam.R

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        findViewById<AppCompatButton>(R.id.signin_confirm_btn).setOnClickListener {
            val email = findViewById<TextView>(R.id.signin_email_input).text.toString()
            val password = findViewById<TextView>(R.id.signin_password_input).text.toString()
            signIn(email, password);
        }

        findViewById<AppCompatButton>(R.id.signin_goto_signup_btn).setOnClickListener {
            startActivity(Intent(applicationContext, SignUpActivity::class.java))
            finish()
        }

        findViewById<TextView>(R.id.signin_goto_lpass_btn).setOnClickListener {
            startActivity(Intent(applicationContext, LostPasswordActivity::class.java))
            finish()
        }
    }

    fun signIn(email: String, password: String) {
        if (email == "test" && password == "test") {
            startActivity(Intent(applicationContext, HomeActivity::class.java))
            finish()
        }
        else {
            Toast.makeText(
            this@SignInActivity,
            "Invalid credentials. Try again.",
            Toast.LENGTH_SHORT).show()
        }
    }
}