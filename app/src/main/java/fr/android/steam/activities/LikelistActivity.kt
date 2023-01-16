package fr.android.steam.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.android.steam.adapters.GameAdapter
import fr.android.steam.R
import fr.android.steam.models.ApplicationUser

class LikelistActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_likelist)

        supportActionBar?.setCustomView(R.layout.action_bar_return)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        findViewById<TextView>(R.id.navbar_title).text = "Mes likes"

        val bundle = intent.getBundleExtra("_bundle")
        val user = bundle?.getParcelable("_user") as ApplicationUser?

        recyclerView = findViewById(R.id.likelist_list)
        recyclerView.layoutManager = LinearLayoutManager(this@LikelistActivity)
        val adapter = user?.likedGames?.let { GameAdapter(it) }
        recyclerView.adapter = adapter
    }
}