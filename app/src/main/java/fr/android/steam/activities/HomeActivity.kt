package fr.android.steam.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.android.steam.GameAdapter
import fr.android.steam.R
import fr.android.steam.services.GameService

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val recyclerview = findViewById<RecyclerView>(R.id.home_topsales_list)
        recyclerview.layoutManager = LinearLayoutManager(this)
        val adapter = GameAdapter(GameService().mockGames)
        recyclerview.adapter = adapter
    }
}