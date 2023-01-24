package fr.android.steam.services

import android.content.Context
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import com.android.volley.Request

class RequestFactory() {

    companion object {

        suspend fun generateGetRequest(context: Context, url: String): JSONObject = suspendCoroutine { cont ->
            // Instantiate the RequestQueue.
            val queue = Volley.newRequestQueue(context)

            // Request a string response from the provided URL.
            val jsonRequest = JsonObjectRequest(Request.Method.GET, url, null,
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

        suspend fun generatePutRequest(context: Context, url: String): JSONObject = suspendCoroutine { cont ->
            // Instantiate the RequestQueue.
            val queue = Volley.newRequestQueue(context)

            // Request a string response from the provided URL.
            val jsonRequest = JsonObjectRequest(Request.Method.PUT, url, null,
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

        suspend fun generatePostRequest(context: Context, url: String, body: JSONObject): JSONObject = suspendCoroutine { cont ->
            // Instantiate the RequestQueue.
            val queue = Volley.newRequestQueue(context)

            // Request a string response from the provided URL.
            val jsonRequest = JsonObjectRequest(Request.Method.POST, url, body,
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

}