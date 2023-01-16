package fr.android.steam.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.android.steam.adapters.GameAdapter
import fr.android.steam.R
import fr.android.steam.models.ApplicationUser
import fr.android.steam.services.GameService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class HomeActivity : AppCompatActivity(), CoroutineScope {

    protected lateinit var job: Job
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main

    private lateinit var recyclerView: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportActionBar?.setCustomView(R.layout.action_bar_home)
        supportActionBar?.setDisplayShowCustomEnabled(true)

        val bundle = intent.getBundleExtra("_bundle")
        val user = bundle?.getParcelable("_user") as ApplicationUser?

        recyclerView = findViewById(R.id.home_topsales_list)
        recyclerView.layoutManager = LinearLayoutManager(this@HomeActivity)
        val adapter = GameAdapter(listOf())
        recyclerView.adapter = adapter

        getTopGames()

        findViewById<AppCompatButton>(R.id.home_search_input).setOnClickListener {
            startActivity(Intent(applicationContext, SearchActivity::class.java))
            finish()
        }

        findViewById<ImageButton>(R.id.navbar_button_like).setOnClickListener {
            if (user?.likedGames?.isEmpty() == true) {
                startActivity(Intent(applicationContext, EmptyLikelistActivity::class.java))
                finish()
            }
            else {
                val bundle = Bundle()
                bundle.putParcelable("_user", user)
                val i = Intent(applicationContext, LikelistActivity::class.java)
                i.putExtra("_bundle", bundle)
                startActivity(i)
                finish()
            }
        }

        findViewById<ImageButton>(R.id.navbar_button_wishlist).setOnClickListener {
            if (user?.wishListedGames?.isEmpty() == true) {
                startActivity(Intent(applicationContext, EmptyWishlistActivity::class.java))
                finish()
            }
            else {
                val bundle = Bundle()
                bundle.putParcelable("_user", user)
                val i = Intent(applicationContext, WishlistActivity::class.java)
                i.putExtra("_bundle", bundle)
                startActivity(i)
                finish()
            }
        }
    }

    private fun getTopGames() {
        job = Job()
        launch {
            val data = GameService(this@HomeActivity).getTopGames()

            if (data.has("error")) {
                Toast.makeText(
                this@HomeActivity,
                "Une erreur est survenue",
                Toast.LENGTH_SHORT).show()
            }
            else {
                val games = GameService(this@HomeActivity).parseJSONGames(data.getJSONArray("games"))
                val adapter = GameAdapter(games.subList(1, games.size))
                recyclerView.adapter = adapter

                val backgroundImage = findViewById<ImageView>(R.id.home_game_background_image)
                Glide.with(this@HomeActivity)
                    .load(games[0].background_image)
                    .centerCrop()
                    .into(backgroundImage);

                val miniImage = findViewById<ImageView>(R.id.home_game_mini_image)
                Glide.with(this@HomeActivity)
                    .load(games[0].mini_image)
                    .centerCrop()
                    .into(miniImage);

                findViewById<TextView>(R.id.home_game_name_label).text = games[0].name
                findViewById<TextView>(R.id.home_game_desc_label).text = games[0].description

                findViewById<AppCompatButton>(R.id.home_game_details_btn).setOnClickListener {
                    val bundle = Bundle()
                    bundle.putParcelable("_game", games[0])
                    val i = Intent(applicationContext, GameDetailsActivity::class.java)
                    i.putExtra("_bundle", bundle)
                    startActivity(i)
                    finish()
                }

            }
        }
    }
}