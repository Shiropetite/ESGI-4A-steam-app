package fr.android.steam.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class SignInService(
    val context: Context
) : Service() {

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    suspend fun signIn(email: String, password: String): JSONObject
        = suspendCoroutine { cont ->
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        val url = getSignInUrl(email, password)

        // Request a string response from the provided URL.
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                cont.resume(response)
            },
            { error ->
                // panic
            }
        )

        // Add the request to the RequestQueue.
        queue.add(jsonRequest)
    }

    private fun getSignInUrl(email: String, password: String): String {
        return "http://10.0.2.2:3000/users/signin?email=${email}&pwd=${password}"
    }

}