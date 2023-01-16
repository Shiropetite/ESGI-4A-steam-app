package fr.android.steam.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import fr.android.steam.models.Game
import fr.android.steam.models.GameReview
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class GameService(
    val context: Context
) : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    suspend fun getTopGames(): JSONObject
            = suspendCoroutine { cont ->
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        val url = getTopGamesURL()

        // Request a string response from the provided URL.
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                cont.resume(response)
            },
            {
                cont.resume(JSONObject().put("error", "error_occurred"))
            }
        )

        // Add the request to the RequestQueue.
        queue.add(jsonRequest)
    }

    private fun getTopGamesURL(): String {
        return "http://10.0.2.2:3000/games/top"
    }

    suspend fun findByName(name: String): JSONObject
            = suspendCoroutine { cont ->
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        val url = findByNameUrl(name)

        // Request a string response from the provided URL.
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                cont.resume(response)
            },
            {
                cont.resume(JSONObject().put("error", "error_occurred"))
            }
        )

        // Add the request to the RequestQueue.
        queue.add(jsonRequest)
    }

    private fun findByNameUrl(name: String): String {
        return "http://10.0.2.2:3000/games/search?name=$name"
    }

    fun parseJSONGames(jsonGames: JSONArray): List<Game> {
        val games: MutableList<Game> = mutableListOf()
        (0 until jsonGames.length()).forEach { i ->
            val currentGame: JSONObject = jsonGames.get(i) as JSONObject
            games.add(Game(
                currentGame.getString("id"),
                currentGame.getString("name"),
                currentGame.getString("description"),
                currentGame.getString("publisher"),
                currentGame.getString("price"),
                currentGame.getString("mini_image"),
                currentGame.getString("bg_image"),
                currentGame.getString("cover_image")))
        }
        return games
    }
}
