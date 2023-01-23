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

class UserService(val context: Context) : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    suspend fun like(idUser: String, idGame: String): JSONObject = suspendCoroutine { cont ->
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        val url = "http://10.0.2.2:3000/users/$idUser/add-like/$idGame"

        // Request a string response from the provided URL.
        val jsonRequest = JsonObjectRequest(
            Request.Method.PUT, url, null,
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

    suspend fun unlike(idUser: String, idGame: String): JSONObject = suspendCoroutine { cont ->
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        val url = "http://10.0.2.2:3000/users/$idUser/remove-like/$idGame"

        // Request a string response from the provided URL.
        val jsonRequest = JsonObjectRequest(
            Request.Method.PUT, url, null,
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

    suspend fun wish(idUser: String, idGame: String): JSONObject = suspendCoroutine { cont ->
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        val url = "http://10.0.2.2:3000/users/$idUser/add-wishlist/$idGame"

        // Request a string response from the provided URL.
        val jsonRequest = JsonObjectRequest(
            Request.Method.PUT, url, null,
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

    suspend fun unwish(idUser: String, idGame: String): JSONObject = suspendCoroutine { cont ->
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        val url = "http://10.0.2.2:3000/users/$idUser/remove-wishlist/$idGame"

        // Request a string response from the provided URL.
        val jsonRequest = JsonObjectRequest(
            Request.Method.PUT, url, null,
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

}