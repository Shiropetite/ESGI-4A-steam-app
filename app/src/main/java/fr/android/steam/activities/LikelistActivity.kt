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

class LikelistActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_likelist)

        val user = SessionStorage.getCurrentUser()

        initNavbar()

        if (user.likedGames?.isEmpty() == true) {
            setContentView(R.layout.activity_empty_likelist)
        }
        else {
            recyclerView = findViewById(R.id.likelist_list)
            recyclerView.layoutManager = LinearLayoutManager(this@LikelistActivity)
            val adapter = user.likedGames?.let { GameAdapter(it) }
            recyclerView.adapter = adapter
        }
    }

    private fun initNavbar() {
        supportActionBar?.setCustomView(R.layout.action_bar_return)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        findViewById<TextView>(R.id.navbar_title).text = getString(R.string.my_likes)

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