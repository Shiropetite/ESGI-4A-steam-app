package fr.android.steam.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import fr.android.steam.models.GameReview
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class GameReviewService(
    val context: Context
) : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    suspend fun getGameReviews(id: String): JSONObject
            = suspendCoroutine { cont ->
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        val url = getGameReviewsUrl(id)

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

    private fun getGameReviewsUrl(id: String): String {
        return "http://10.0.2.2:3000/games/$id/reviews"
    }

    fun parseJSONGameReviews(jsonReviews: JSONArray): List<GameReview> {
        val reviews: MutableList<GameReview> = mutableListOf()
        (0 until jsonReviews.length()).forEach { i ->
            val currentGame: JSONObject = jsonReviews.get(i) as JSONObject
            reviews.add(
                GameReview(
                currentGame.getString("name"),
                currentGame.getBoolean("good_grade"),
                currentGame.getString("review"))
            )
        }
        return reviews
    }
}