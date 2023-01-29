package fr.android.steam.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import fr.android.steam.R
import fr.android.steam.models.ApplicationUser
import fr.android.steam.services.GameParser
import fr.android.steam.services.RequestFactory
import fr.android.steam.services.SessionStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONObject
import kotlin.coroutines.CoroutineContext

class LostPasswordActivity : AppCompatActivity(), CoroutineScope {

    protected lateinit var job: Job
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lost_password)

        supportActionBar?.hide()

        findViewById<AppCompatButton>(R.id.lpass_confirm_btn).setOnClickListener {
            val email = findViewById<TextView>(R.id.lpass_email_input).text.toString()
            val newPassword = findViewById<TextView>(R.id.lpass_password_input).text.toString()
            changePassword(email, newPassword)
        }

        findViewById<AppCompatButton>(R.id.lpass_goto_signin_btn).setOnClickListener {
            startActivity(Intent(applicationContext, SignInActivity::class.java))
            finish()
        }
    }

    private fun changePassword(email: String, newPassword: String) {
        job = Job()
        launch {
            val data = RequestFactory.generatePutRequest(
                this@LostPasswordActivity,
                "http://10.0.2.2:3000/users/change-password?email=$email&pwd=$newPassword"
            )

            if (data.has("error")) {
                Toast.makeText(
                this@LostPasswordActivity,
                getString(R.string.error_lost_password),
                Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(
                this@LostPasswordActivity,
                getString(R.string.success_lost_password),
                Toast.LENGTH_SHORT).show()

                startActivity(Intent(applicationContext, SignInActivity::class.java))
                finish()
            }
        }
    }
}