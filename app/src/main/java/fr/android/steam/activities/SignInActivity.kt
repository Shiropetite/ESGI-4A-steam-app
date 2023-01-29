package fr.android.steam.activities

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import fr.android.steam.R
import fr.android.steam.models.ApplicationUser
import fr.android.steam.services.GameParser
import fr.android.steam.services.SessionStorage
import fr.android.steam.services.RequestFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class SignInActivity : AppCompatActivity(), CoroutineScope {

    protected lateinit var job: Job
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        supportActionBar?.hide()

        StrictMode.setThreadPolicy(ThreadPolicy.Builder().permitAll().build())

        findViewById<AppCompatButton>(R.id.signin_confirm_btn).setOnClickListener {
            val email = findViewById<TextView>(R.id.signin_email_input).text.toString()
            val password = findViewById<TextView>(R.id.signin_password_input).text.toString()
            signIn(email, password)
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

    private fun signIn(email: String, password: String) {
        job = Job()
        launch {
            val data = RequestFactory.generateGetRequest(
                this@SignInActivity,
                "http://10.0.2.2:3000/users/signin?email=$email&pwd=$password"
            )

            if (data.has("error")) {
                Toast.makeText(
                    this@SignInActivity,
                    getString(R.string.error_sign_in),
                    Toast.LENGTH_SHORT).show()
            }
            else {
                val user = ApplicationUser(
                    data.getString("id"),
                    data.getString("name"),
                    data.getString("email"),
                    data.getString("password"),
                    GameParser.parseJSONGames(data.getJSONArray("likedGames")),
                    GameParser.parseJSONGames(data.getJSONArray("wishlistedGames")),
                )

                SessionStorage.setCurrentUser(user)
                startActivity(Intent(applicationContext, HomeActivity::class.java))
                finish()
            }
        }
    }
}