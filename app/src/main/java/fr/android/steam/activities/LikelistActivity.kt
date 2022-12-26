package fr.android.steam.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.android.steam.GameAdapter
import fr.android.steam.R
import fr.android.steam.models.ApplicationUser

class LikelistActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_likelist)

        supportActionBar?.setCustomView(R.layout.action_bar)
        supportActionBar?.setDisplayShowCustomEnabled(true)

        val bundle = intent.getBundleExtra("_bundle")
        val user = bundle?.getParcelable("_user") as ApplicationUser?

        recyclerView = findViewById(R.id.likelist_list)
        recyclerView.layoutManager = LinearLayoutManager(this@LikelistActivity)
        val adapter = user?.likedGames?.let { GameAdapter(this, it) }
        recyclerView.adapter = adapter
    }
}