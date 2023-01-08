package fr.android.steam.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.android.steam.adapters.GameAdapter
import fr.android.steam.R
import fr.android.steam.models.ApplicationUser

class WishlistActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist)

        supportActionBar?.setCustomView(R.layout.action_bar)
        supportActionBar?.setDisplayShowCustomEnabled(true)

        val bundle = intent.getBundleExtra("_bundle")
        val user = bundle?.getParcelable("_user") as ApplicationUser?

        recyclerView = findViewById(R.id.wishlist_list)
        recyclerView.layoutManager = LinearLayoutManager(this@WishlistActivity)
        val adapter = user?.wishListedGames?.let { GameAdapter(this, it) }
        recyclerView.adapter = adapter
    }
}