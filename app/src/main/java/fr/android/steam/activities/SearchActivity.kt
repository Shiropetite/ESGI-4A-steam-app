package fr.android.steam.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.android.steam.adapters.GameAdapter
import fr.android.steam.R
import fr.android.steam.services.GameService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SearchActivity : AppCompatActivity(), CoroutineScope {

    protected lateinit var job: Job
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        supportActionBar?.setCustomView(R.layout.action_bar)
        supportActionBar?.setDisplayShowCustomEnabled(true)

        recyclerView = findViewById(R.id.search_results)
        recyclerView.layoutManager = LinearLayoutManager(this@SearchActivity)
        val adapter = GameAdapter(this, listOf())
        recyclerView.adapter = adapter

        findViewById<EditText>(R.id.search_searchbar).addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) { }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }
            override fun afterTextChanged(s: Editable) {
                if (s.isNotEmpty()) {
                    findByName(s.toString())
                }
            }
        })
    }

    private fun findByName(name: String) {
        job = Job()
        launch {
            val data = GameService(this@SearchActivity).findByName(name)

            if (data.has("error")) {
                Toast.makeText(
                    this@SearchActivity,
                    "Une erreur est survenue",
                    Toast.LENGTH_SHORT).show()
            }
            else {
                val count = data.getInt("count")
                val games = GameService(this@SearchActivity).parseJSONGames(data.getJSONArray("games"))
                findViewById<TextView>(R.id.search_results_title).text = "Nombre de résultats : $count"
                val adapter = GameAdapter(this@SearchActivity, games)
                recyclerView.adapter = adapter
            }
        }
    }
}