package fr.android.steam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        supportActionBar?.hide(); // Hide action bar on top

        // Set error style on input
        /*
        val inputPassword = findViewById<EditText>(R.id.input_password);
        inputPassword.setBackgroundResource(R.drawable.error_input);
        inputPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.warning, 0);
        */
    }
}