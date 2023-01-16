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

class WishlistActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist)

        supportActionBar?.setCustomView(R.layout.action_bar_return)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        findViewById<TextView>(R.id.navbar_title).text = "Ma liste de souhaits"

        val bundle = intent.getBundleExtra("_bundle")
        val user = bundle?.getParcelable("_user") as ApplicationUser?

        recyclerView = findViewById(R.id.wishlist_list)
        recyclerView.layoutManager = LinearLayoutManager(this@WishlistActivity)
        val adapter = user?.wishListedGames?.let { GameAdapter(it) }
        recyclerView.adapter = adapter
    }
}