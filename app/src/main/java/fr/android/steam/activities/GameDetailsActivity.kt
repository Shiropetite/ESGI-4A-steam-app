package fr.android.steam.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.bumptech.glide.Glide
import fr.android.steam.R
import fr.android.steam.models.ApplicationUser
import fr.android.steam.models.Game

class GameDetailsActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_details)

        val bundle = intent.getBundleExtra("_bundle")
        val game = bundle?.getParcelable("_game") as Game?

        findViewById<TextView>(R.id.game_details_name).text = game?.name
        findViewById<TextView>(R.id.game_details_publisher).text = game?.publisher
        findViewById<TextView>(R.id.game_details_price).text = game?.price
        findViewById<TextView>(R.id.game_details_text).text = game?.description

        Glide.with(applicationContext)
            .load(game?.mini_image)
            .centerCrop()
            .into(findViewById(R.id.game_details_img))

        findViewById<AppCompatButton>(R.id.game_details_goto_reviews).setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("_game", game)
            val i = Intent(applicationContext, GameReviewsActivity::class.java)
            i.putExtra("_bundle", bundle)
            startActivity(i)
            finish()
        }
    }
}