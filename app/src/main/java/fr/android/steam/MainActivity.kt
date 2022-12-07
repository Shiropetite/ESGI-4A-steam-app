package fr.android.steam

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lost_password)

        // Show action bar
        supportActionBar?.setCustomView(R.layout.action_bar)
        supportActionBar?.setDisplayShowCustomEnabled(true)

        /* Test Image button in navbar
        findViewById<ImageButton>(R.id.navbar_button_like).setOnClickListener {
            Toast.makeText(this@MainActivity, "You clicked me.", Toast.LENGTH_SHORT).show()
        }

        findViewById<ImageButton>(R.id.navbar_button_wishlist).setOnClickListener {
            Toast.makeText(this@MainActivity, "You clicked me.", Toast.LENGTH_SHORT).show()
        }
        */

        // Hide action bar on top
        // supportActionBar?.hide();

        // Set error style on input
        /*
            val inputPassword = findViewById<EditText>(R.id.sign_in_input_email);
            inputPassword.setBackgroundResource(R.drawable.error_input);
            inputPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.warning, 0);
         */

    }
}