package fr.android.steam.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import fr.android.steam.R

class LostPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lost_password)

        findViewById<AppCompatButton>(R.id.lpass_confirm_btn).setOnClickListener {
            val email = findViewById<TextView>(R.id.lpass_email_input).text.toString()
            resetPassword(email)
        }

        findViewById<AppCompatButton>(R.id.lpass_goto_signin_btn).setOnClickListener {
            startActivity(Intent(applicationContext, SignInActivity::class.java))
            finish()
        }
    }

    fun resetPassword(email: String) {
        Toast.makeText(
        this@LostPasswordActivity,
        "Reset password email sent to $email",
        Toast.LENGTH_SHORT).show()
    }
}