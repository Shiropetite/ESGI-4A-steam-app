package fr.android.steam.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class SignUpService(
    val context: Context
) : Service() {

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    suspend fun signUp(name: String, email: String, password: String): JSONObject
        = suspendCoroutine { cont ->
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        val url = getSignUpUrl();

        val body = JSONObject()
        body.put("name", name);
        body.put("email", email);
        body.put("password", password);

        // Request a string response from the provided URL.
        val jsonRequest = JsonObjectRequest(
            Request.Method.POST, url, body,
            { response ->
                cont.resume(response)
            },
            {
                cont.resume(JSONObject().put("error", "user_already_exist"))
            },
        )

        // Add the request to the RequestQueue.
        queue.add(jsonRequest)
    }

    private fun getSignUpUrl(): String {
        return "http://10.0.2.2:3000/users/signup"
    }

}
