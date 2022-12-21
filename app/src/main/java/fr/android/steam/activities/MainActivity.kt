package fr.android.steam.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.android.steam.R

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty_wishlist)

        // Show action bar
        supportActionBar?.setCustomView(R.layout.action_bar)
        supportActionBar?.setDisplayShowCustomEnabled(true)

        // Create list (TODO: Update when logic is implemented)
        /*
        val recyclerview = findViewById<RecyclerView>(R.id.search_result_list)
        recyclerview.layoutManager = LinearLayoutManager(this)
        val data = ArrayList<String>()
        for (i in 1..20) {
            data.add("Item $i")
        }
        val adapter = CustomAdapter(data)
        recyclerview.adapter = adapter
         */

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