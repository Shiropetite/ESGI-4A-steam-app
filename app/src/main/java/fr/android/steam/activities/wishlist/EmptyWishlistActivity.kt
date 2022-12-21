package fr.android.steam.activities.wishlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.android.steam.R

class EmptyWishlistActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty_wishlist)
    }
}