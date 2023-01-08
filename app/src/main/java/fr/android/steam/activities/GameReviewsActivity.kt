package fr.android.steam.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.android.steam.R
import fr.android.steam.adapters.GameAdapter
import fr.android.steam.adapters.GameReviewAdapter
import fr.android.steam.models.ApplicationUser
import fr.android.steam.models.Game
import fr.android.steam.models.GameReview
import fr.android.steam.services.GameReviewService
import fr.android.steam.services.GameService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class GameReviewsActivity : AppCompatActivity(), CoroutineScope {

    protected lateinit var job: Job
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main

    private lateinit var recyclerView: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_reviews)

        val bundle = intent.getBundleExtra("_bundle")
        val game = bundle?.getParcelable("_game") as Game?

        recyclerView = findViewById(R.id.game_reviews_list)
        recyclerView.layoutManager = LinearLayoutManager(this@GameReviewsActivity)
        val adapter = GameReviewAdapter(listOf())
        recyclerView.adapter = adapter

        findViewById<TextView>(R.id.game_reviews_name).text = game?.name
        findViewById<TextView>(R.id.game_reviews_publisher).text = game?.publisher
        findViewById<TextView>(R.id.game_reviews_price).text = game?.price

        Glide.with(applicationContext)
            .load(game?.mini_image)
            .centerCrop()
            .into(findViewById(R.id.game_reviews_img))

        getGameReviews(game?.id.orEmpty())

        findViewById<AppCompatButton>(R.id.game_reviews_goto_details).setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("_game", game)
            val i = Intent(applicationContext, GameDetailsActivity::class.java)
            i.putExtra("_bundle", bundle)
            startActivity(i)
            finish()
        }
    }

    private fun getGameReviews(id: String) {
        job = Job()
        launch {
            val data = GameReviewService(this@GameReviewsActivity).getGameReviews(id)

            if (data.has("error")) {
                Toast.makeText(
                    this@GameReviewsActivity,
                    "Une erreur est survenue",
                    Toast.LENGTH_SHORT).show()
            }
            else {
                val reviews = listOf(
                    GameReview("review1", true, "eoirgoeirjgozeurhguoieroiguehrgo iunrgi znroibunoirun"),
                    GameReview("review2", false, "eorigjoe igoeir joeirjg oeijrgoizjrogiej ori"),
                    GameReview("review3", true, "egoirjg oejirgoi ejrgoie jorigje oirjgeoirj goeijr goeijrg")
                )
                //val reviews = GameReviewService(this@GameReviewsActivity).parseJSONGameReviews(data.getJSONArray("reviews"))
                val adapter = GameReviewAdapter(reviews)
                recyclerView.adapter = adapter
            }
        }
    }
}