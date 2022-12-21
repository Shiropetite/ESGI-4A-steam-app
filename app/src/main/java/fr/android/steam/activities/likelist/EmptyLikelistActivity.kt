package fr.android.steam.activities.likelist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.android.steam.R

class EmptyLikelistActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty_likelist)
    }
}