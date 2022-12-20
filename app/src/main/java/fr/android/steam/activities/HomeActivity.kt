package fr.android.steam.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.android.steam.GameAdapter
import fr.android.steam.models.ApplicationUser
import fr.android.steam.models.Game
import fr.android.steam.services.GameService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.CoroutineContext


class HomeActivity : AppCompatActivity(), CoroutineScope {
    protected lateinit var job: Job
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main
    private lateinit var recyclerView: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(fr.android.steam.R.layout.activity_home)

        supportActionBar?.setCustomView(fr.android.steam.R.layout.action_bar)
        supportActionBar?.setDisplayShowCustomEnabled(true)

        val user = intent.extras?.getParcelable("fr.android.steam.models.ApplicationUser") as ApplicationUser?
        Log.d("@@@@@@@@@@@@@@@@@@@@@@@", user.toString())

        recyclerView = findViewById(fr.android.steam.R.id.home_topsales_list)
        recyclerView.layoutManager = LinearLayoutManager(this@HomeActivity)
        val adapter = GameAdapter(this, listOf())
        recyclerView.adapter = adapter

        getTopGames()

        findViewById<TextView>(fr.android.steam.R.id.home_search_input).setOnClickListener {
            startActivity(Intent(applicationContext, SearchActivity::class.java))
            finish()
        }

        findViewById<ImageButton>(fr.android.steam.R.id.navbar_button_like).setOnClickListener {
            Toast.makeText(this@HomeActivity, "You clicked me.", Toast.LENGTH_SHORT).show()
        }

        findViewById<ImageButton>(fr.android.steam.R.id.navbar_button_wishlist).setOnClickListener {
            Toast.makeText(this@HomeActivity, "You clicked me.", Toast.LENGTH_SHORT).show()
        }
    }

    fun getTopGames() {
        job = Job()
        launch {
            val data = GameService(this@HomeActivity).getTopGames()

            if(data.has("error")) {
                Toast.makeText(
                    this@HomeActivity,
                    "Une erreur est intervenue",
                    Toast.LENGTH_SHORT).show()
            }
            else {
                val gameList = parseJSONGames(data.getJSONArray("games"))

                val backgroundImage = findViewById<ImageView>(fr.android.steam.R.id.home_game_background_image)
                Glide.with(this@HomeActivity)
                    .load(gameList[0].background_image)
                    .centerCrop()
                    .into(backgroundImage);

                val miniImage = findViewById<ImageView>(fr.android.steam.R.id.home_game_mini_image)
                Glide.with(this@HomeActivity)
                    .load(gameList[0].mini_image)
                    .centerCrop()
                    .into(miniImage);

                findViewById<TextView>(fr.android.steam.R.id.home_game_name_label).text = gameList[0].name
                findViewById<TextView>(fr.android.steam.R.id.home_game_desc_label).text = gameList[0].description

                // add list
                val adapter = GameAdapter(this@HomeActivity, gameList.subList(1, gameList.size))
                recyclerView.adapter = adapter

            }
        }
    }

    private fun parseJSONGames(jsonGames: JSONArray): List<Game> {
        val games: MutableList<Game> = mutableListOf()
        (0 until jsonGames.length()).forEach { i ->
            val currentGame: JSONObject = jsonGames.get(i) as JSONObject
            games.add(Game(
                currentGame.getString("name"),
                currentGame.getString("description"),
                currentGame.getString("publisher"),
                currentGame.getString("price"),
                currentGame.getString("mini_image"),
                currentGame.getString("bg_image")))
        }
        return games
    }
}