package fr.android.steam.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import fr.android.steam.models.ApplicationUser
import fr.android.steam.models.Game
import fr.android.steam.models.GameReview
import fr.android.steam.services.GameReviewService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class GameDetailsActivity : AppCompatActivity(), CoroutineScope {

    protected lateinit var job: Job
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main

    private lateinit var recyclerView: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_details)

        val bundle = intent.getBundleExtra("_bundle")
        val game = bundle?.getParcelable("_game") as Game?

        findViewById<TextView>(R.id.game_details_name).text = game?.name
        findViewById<TextView>(R.id.game_details_publisher).text = game?.publisher
        findViewById<TextView>(R.id.game_details_price).text = game?.price

        Glide.with(applicationContext)
            .load(game?.mini_image)
            .centerCrop()
            .into(findViewById(R.id.game_details_img))

        Glide.with(applicationContext)
            .load(game?.cover_image)
            .centerCrop()
            .into(findViewById(R.id.game_details_cover_img))

        val description = findViewById<TextView>(R.id.game_details_text)
        description.text = game?.description

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

        getGameReviews(game?.id.orEmpty())
    }

    private fun getGameReviews(id: String) {
        job = Job()
        launch {
            val data = GameReviewService(this@GameDetailsActivity).getGameReviews(id)
            Log.d("########", "Coucou")

            if (data.has("error")) {
                Toast.makeText(
                    this@GameDetailsActivity,
                    "Une erreur est survenue",
                    Toast.LENGTH_SHORT).show()
            }
            else {
                val reviews = GameReviewService(this@GameDetailsActivity).parseJSONGameReviews(data.getJSONArray("reviews"))
                val adapter = GameReviewAdapter(reviews)
                recyclerView.adapter = adapter
            }
        }
    }
}