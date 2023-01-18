package fr.android.steam.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import fr.android.steam.R
import fr.android.steam.models.ApplicationUser
import fr.android.steam.services.GameService
import fr.android.steam.services.SessionService
import fr.android.steam.services.SignUpService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SignUpActivity : AppCompatActivity(), CoroutineScope {

    protected lateinit var job: Job
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()

        findViewById<AppCompatButton>(R.id.signup_confirm_btn).setOnClickListener {
            val username = findViewById<TextView>(R.id.signup_username_input).text.toString()
            val email = findViewById<TextView>(R.id.signup_email_input).text.toString()
            val password = findViewById<TextView>(R.id.signup_password_input).text.toString()
            val verificationPassword = findViewById<TextView>(R.id.signup_cpassword_input).text.toString()
            signUp(username, email, password, verificationPassword)
        }

        findViewById<AppCompatButton>(R.id.signup_goto_signin_btn).setOnClickListener {
            startActivity(Intent(applicationContext, SignInActivity::class.java))
            finish()
        }
    }

    private fun signUp(name: String, email: String, password: String, verificationPassword: String) {
        if (password != verificationPassword) {
            Toast.makeText(
                this@SignUpActivity,
                "Le mot de passe est différent de sa vérification",
                Toast.LENGTH_SHORT).show()
            return;
        }

        job = Job()
        launch {
            val data = SignUpService(this@SignUpActivity).signUp(name, email, password)

            if (data.has("error")) {
                Toast.makeText(
                this@SignUpActivity,
                "Cet adresse email est déjà utilisée",
                Toast.LENGTH_SHORT).show()
            }
            else {
                val user = ApplicationUser(
                    data.getString("name"),
                    data.getString("email"),
                    data.getString("password"),
                    GameService(this@SignUpActivity).parseJSONGames(data.getJSONArray("likedGames")),
                    GameService(this@SignUpActivity).parseJSONGames(data.getJSONArray("wishlistedGames")),
                )

                SessionService.setCurrentUser(user)

                startActivity(Intent(applicationContext, HomeActivity::class.java))
                finish()
            }
        }
    }
}