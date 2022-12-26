package fr.android.steam.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.android.steam.R

class GameReviewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_reviews)

        supportActionBar?.setCustomView(R.layout.action_bar)
        supportActionBar?.setDisplayShowCustomEnabled(true)
    }
}