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
import fr.android.steam.services.SessionStorage
import fr.android.steam.services.RequestFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.regex.Pattern
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
        val name_input =findViewById<TextView>(R.id.signup_username_input)
        name_input.background = getDrawable(R.drawable.normal_input)
        name_input.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

        if (name.isBlank()) {
            name_input.background = getDrawable(R.drawable.error_input)
            name_input.setCompoundDrawablesWithIntrinsicBounds(null, null, getDrawable(R.drawable.warning), null)

            Toast.makeText(
                this@SignUpActivity,
                getString(R.string.error_username_empty),
                Toast.LENGTH_SHORT).show()
            return
        }

        val emailInput = findViewById<TextView>(R.id.signup_email_input)
        emailInput.background = getDrawable(R.drawable.normal_input)
        emailInput.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

        if (!isValidEmail(email)) {
            emailInput.background = getDrawable(R.drawable.error_input)
            emailInput.setCompoundDrawablesWithIntrinsicBounds(null, null, getDrawable(R.drawable.warning), null)

            Toast.makeText(
                this@SignUpActivity,
                getString(R.string.error_email_invalid),
                Toast.LENGTH_SHORT).show()
            return
        }

        val password_input =findViewById<TextView>(R.id.signup_password_input)
        password_input.background = getDrawable(R.drawable.normal_input)
        password_input.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

        if (password.isBlank()) {
            password_input.background = getDrawable(R.drawable.error_input)
            password_input.setCompoundDrawablesWithIntrinsicBounds(null, null, getDrawable(R.drawable.warning), null)

            Toast.makeText(
                this@SignUpActivity,
                getString(R.string.error_password_empty),
                Toast.LENGTH_SHORT).show()
            return
        }

        val password_verification =findViewById<TextView>(R.id.signup_cpassword_input)
        password_verification.background = getDrawable(R.drawable.normal_input)
        password_verification.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

        if (password != verificationPassword) {
            password_verification.background = getDrawable(R.drawable.error_input)
            password_verification.setCompoundDrawablesWithIntrinsicBounds(null, null, getDrawable(R.drawable.warning), null)

            Toast.makeText(
                this@SignUpActivity,
                getString(R.string.error_password_verification),
                Toast.LENGTH_SHORT).show()
            return
        }

        job = Job()
        launch {
            val body = JSONObject()
            body.put("name", name)
            body.put("email", email)
            body.put("password", password)

            val data = RequestFactory.generatePostRequest(
                this@SignUpActivity,
                "http://10.0.2.2:3000/users/signup",
                body
            )

            if (data.has("error")) {
                emailInput.background = getDrawable(R.drawable.error_input)
                emailInput.setCompoundDrawablesWithIntrinsicBounds(null, null, getDrawable(R.drawable.warning), null)

                Toast.makeText(
                this@SignUpActivity,
                getString(R.string.error_sign_up),
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

    fun isValidEmail(email: String): Boolean {
        val pattern = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
        )
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }
}