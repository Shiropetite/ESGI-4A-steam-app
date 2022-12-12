package fr.android.steam.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import fr.android.steam.models.Game

class GameService(
    val mockGames: List<Game> = listOf(
        Game("CSGO", "publisher", 20.0),
        Game("Overwatch 2", "publisher", 65.0),
        Game("Minecraft", "publisher", 15.0))
) : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    fun getTopGames() { }
}
