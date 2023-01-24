package fr.android.steam.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.android.steam.R
import fr.android.steam.adapters.GameReviewAdapter
import fr.android.steam.models.Game
import fr.android.steam.services.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class GameDetailsActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main

    private lateinit var recyclerView: RecyclerView
    private lateinit var currentGame: Game

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_details)

        initNavbar()

        // Get game
        val bundle = intent.getBundleExtra("_bundle")
        this.currentGame = bundle?.getParcelable<Game>("_game")!!

        // Get user
        val user = SessionStorage.getCurrentUser()

        val wishButton = findViewById<ImageButton>(R.id.game_details_button_wishlist)

        if(user.wishlistedGames?.contains(this.currentGame) == true) { wishButton.setImageResource(R.drawable.wish_full); }

        findViewById<TextView>(R.id.game_details_name).text = this.currentGame.name
        findViewById<TextView>(R.id.game_details_publisher).text = this.currentGame.publisher
        findViewById<TextView>(R.id.game_details_price).text = this.currentGame.price

        Glide.with(applicationContext)
            .load(this.currentGame.mini_image)
            .centerCrop()
            .into(findViewById(R.id.game_details_img))

        Glide.with(applicationContext)
            .load(this.currentGame.cover_image)
            .centerCrop()
            .into(findViewById(R.id.game_details_cover_img))

        val description = findViewById<TextView>(R.id.game_details_text)
        description.text = this.currentGame.description

        recyclerView = findViewById(R.id.game_reviews_list)
        recyclerView.visibility = View.INVISIBLE
        recyclerView.layoutManager = LinearLayoutManager(this@GameDetailsActivity)
        val adapter = GameReviewAdapter(listOf())
        recyclerView.adapter = adapter

        val descriptionBtn = findViewById<AppCompatButton>(R.id.game_details_goto_reviews)
        val reviewBtn = findViewById<AppCompatButton>(R.id.game_details_description)

        descriptionBtn.setOnClickListener {
            description.visibility = View.INVISIBLE
            recyclerView.visibility = View.VISIBLE
            descriptionBtn.setBackgroundResource(R.drawable.primary_button)
            reviewBtn.setBackgroundResource(R.drawable.outlined_button)
        }

        reviewBtn.setOnClickListener {
            recyclerView.visibility = View.INVISIBLE
            description.visibility = View.VISIBLE
            descriptionBtn.setBackgroundResource(R.drawable.outlined_button)
            reviewBtn.setBackgroundResource(R.drawable.primary_button)
        }

        getGameReviews(this.currentGame.id.orEmpty())

        initLikButton()
        initWishButton()
    }

    private fun initNavbar() {
        supportActionBar?.setCustomView(R.layout.action_bar_return)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        findViewById<TextView>(R.id.navbar_title).text = getString(R.string.title_game_detail)

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

    private fun initLikButton() {
        val user = SessionStorage.getCurrentUser()
        val likeButton = findViewById<ImageButton>(R.id.game_details_button_like)

        if(user.likedGames?.contains(this.currentGame) == true) {
            likeButton.setImageResource(R.drawable.like_full)
        }

        likeButton.setOnClickListener {
            job = Job()
            launch {
                // Si déjà liker
                if(user.likedGames?.contains(currentGame) == true) {
                    val data = RequestFactory.generatePutRequest(
                        this@GameDetailsActivity,
                        "http://10.0.2.2:3000/users/${user.id!!}/remove-like/${currentGame.id!!}"
                    )

                    if (data.has("error")) {
                        Toast.makeText(
                            this@GameDetailsActivity,
                            "Une erreur est survenue",
                            Toast.LENGTH_SHORT).show()
                    }
                    else {
                        likeButton.setImageResource(R.drawable.like)
                        user.likedGames = GameParser.parseJSONGames(data.getJSONArray("likedGames"))
                    }
                } // Si pas liker
                else {
                    val data = RequestFactory.generatePutRequest(
                        this@GameDetailsActivity,
                        "http://10.0.2.2:3000/users/${user.id!!}/add-like/${currentGame.id!!}"
                    )

                    if (data.has("error")) {
                        Toast.makeText(
                            this@GameDetailsActivity,
                            "Une erreur est survenue",
                            Toast.LENGTH_SHORT).show()
                    }
                    else {
                        likeButton.setImageResource(R.drawable.like_full)
                        user.likedGames = GameParser.parseJSONGames(data.getJSONArray("likedGames"))
                    }
                }
            }
        }
    }

    private fun initWishButton() {
        val user = SessionStorage.getCurrentUser()
        val wishButton = findViewById<ImageButton>(R.id.game_details_button_wishlist)

        if(user.wishlistedGames?.contains(this.currentGame) == true) {
            wishButton.setImageResource(R.drawable.wish_full)
        }

        wishButton.setOnClickListener {
            job = Job()
            launch {
                // Si déjà wish
                if(user.wishlistedGames?.contains(currentGame) == true) {
                    val data = RequestFactory.generatePutRequest(
                        this@GameDetailsActivity,
                        "http://10.0.2.2:3000/users/${user.id!!}/remove-wishlist/${currentGame.id!!}"
                    )

                    if (data.has("error")) {
                        Toast.makeText(
                            this@GameDetailsActivity,
                            "Une erreur est survenue",
                            Toast.LENGTH_SHORT).show()
                    }
                    else {
                        wishButton.setImageResource(R.drawable.wishlist)
                        user.wishlistedGames = GameParser.parseJSONGames(data.getJSONArray("wishlistedGames"))
                    }
                } // Si pas wish
                else {
                    val data = RequestFactory.generatePutRequest(
                        this@GameDetailsActivity,
                        "http://10.0.2.2:3000/users/${user.id!!}/add-wishlist/${currentGame.id!!}"
                    )

                    if (data.has("error")) {
                        Toast.makeText(
                            this@GameDetailsActivity,
                            "Une erreur est survenue",
                            Toast.LENGTH_SHORT).show()
                    }
                    else {
                        wishButton.setImageResource(R.drawable.wish_full)
                        user.wishlistedGames = GameParser.parseJSONGames(data.getJSONArray("wishlistedGames"))
                    }
                }
            }
        }
    }

    private fun getGameReviews(id: String) {
        job = Job()
        launch {
            val data = RequestFactory.generateGetRequest(
                this@GameDetailsActivity,
                "http://10.0.2.2:3000/games/$id/reviews"
            )

            if (data.has("error")) {
                Toast.makeText(
                    this@GameDetailsActivity,
                    "Une erreur est survenue",
                    Toast.LENGTH_SHORT).show()
            }
            else {
                val reviews = GameParser.parseJSONGameReviews(data.getJSONArray("reviews"))
                val adapter = GameReviewAdapter(reviews)
                recyclerView.adapter = adapter
            }
        }
    }
}