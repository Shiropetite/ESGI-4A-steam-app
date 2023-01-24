package fr.android.steam.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.android.steam.adapters.GameAdapter
import fr.android.steam.R
import fr.android.steam.services.SessionStorage

class WishlistActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist)

        val user = SessionStorage.getCurrentUser()

        initNavbar()

        if (user.wishlistedGames?.isEmpty() == true) {
            setContentView(R.layout.activity_empty_wishlist)
        }
        else {
            recyclerView = findViewById(R.id.wishlist_list)
            recyclerView.layoutManager = LinearLayoutManager(this@WishlistActivity)
            val adapter = user.wishlistedGames?.let { GameAdapter(it) }
            recyclerView.adapter = adapter
        }
    }

    private fun initNavbar() {
        supportActionBar?.setCustomView(R.layout.action_bar_return)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        findViewById<TextView>(R.id.navbar_title).text = getString(R.string.wishlist)

        findViewById<ImageButton>(R.id.navbar_button_back).setOnClickListener {
            startActivity(Intent(applicationContext, HomeActivity::class.java))
            finish()
        }

        findViewById<ImageButton>(R.id.navbar_button_like).setOnClickListener {
            startActivity(Intent(applicationContext, LikelistActivity::class.java))
            finish()
        }

        findViewById<ImageButton>(R.id.navbar_button_wishlist).setOnClickListener {
            startActivity(Intent(applicationContext, WishlistActivity::class.java))
            finish()
        }
    }
}