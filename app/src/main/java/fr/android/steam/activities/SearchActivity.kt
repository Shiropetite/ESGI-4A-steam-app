package fr.android.steam.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.android.steam.adapters.GameAdapter
import fr.android.steam.R
import fr.android.steam.services.GameParser
import fr.android.steam.services.RequestFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SearchActivity : AppCompatActivity(), CoroutineScope {

    protected lateinit var job: Job
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main

    private lateinit var recyclerView: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initNavbar()

        recyclerView = findViewById(R.id.search_results)
        recyclerView.layoutManager = LinearLayoutManager(this@SearchActivity)
        val adapter = GameAdapter(listOf())
        recyclerView.adapter = adapter

        /* recherche avec fire semi-temps réel
         * risque de blacklist de l'api steam
        findViewById<EditText>(R.id.search_searchbar).addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) { }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }
            override fun afterTextChanged(s: Editable) {
                if (s.isNotEmpty()) {
                    findByName(s.toString())
                }
            }
        })
        */

        findViewById<EditText>(R.id.search_searchbar).setOnEditorActionListener(TextView.OnEditorActionListener {
                v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH
                || actionId == EditorInfo.IME_ACTION_DONE
                || event.action == KeyEvent.ACTION_DOWN
                && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                findByName(findViewById<EditText>(R.id.search_searchbar).text.toString())
                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun initNavbar() {
        supportActionBar?.setCustomView(R.layout.action_bar_return)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        findViewById<TextView>(R.id.navbar_title).text = getString(R.string.search)

        findViewById<ImageButton>(R.id.navbar_button_back).setOnClickListener {
            startActivity(Intent(applicationContext, HomeActivity::class.java))
            finish()
        }

        findViewById<ImageButton>(R.id.navbar_button_like).setOnClickListener {
            startActivity(Intent(applicationContext, LikelistActivity::class.java))
            finish()
        }

        findViewById<ImageButton>(R.id.navbar_button_wishlist).setOnClickListener {
            startActivity(Intent(applicationContext, WishlistActivity::class.java))
            finish()
        }
    }

    private fun findByName(name: String) {
        job = Job()
        launch {
            val data = RequestFactory.generateGetRequest(
                this@SearchActivity,
                "http://10.0.2.2:3000/games/search?name=$name"
            )

            if (data.has("error")) {
                Toast.makeText(
                    this@SearchActivity,
                    "Une erreur est survenue",
                    Toast.LENGTH_SHORT).show()
            }
            else {
                val count = data.getInt("count")
                val games = GameParser.parseJSONGames(data.getJSONArray("games"))
                findViewById<TextView>(R.id.search_results_title).text = buildString {
                    append(getString(R.string.number_of_results))
                    append(count)
                }
                val adapter = GameAdapter(games)
                recyclerView.adapter = adapter
            }
        }
    }
}