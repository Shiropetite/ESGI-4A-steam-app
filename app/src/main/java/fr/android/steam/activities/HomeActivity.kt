package fr.android.steam.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.android.steam.GameAdapter
import fr.android.steam.R
import fr.android.steam.models.ApplicationUser
import fr.android.steam.services.GameService

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportActionBar?.setCustomView(R.layout.action_bar)
        supportActionBar?.setDisplayShowCustomEnabled(true)

        val user = intent.extras?.getParcelable("fr.android.steam.models.ApplicationUser") as ApplicationUser?
        Log.d("@@@@@@@@@@@@@@@@@@@@@@@", user.toString())

        val recyclerview = findViewById<RecyclerView>(R.id.home_topsales_list)
        recyclerview.layoutManager = LinearLayoutManager(this)
        val adapter = GameAdapter(GameService().mockGames)
        recyclerview.adapter = adapter

        findViewById<TextView>(R.id.home_search_input).setOnClickListener {
            startActivity(Intent(applicationContext, SearchActivity::class.java))
            finish()
        }
    }
}