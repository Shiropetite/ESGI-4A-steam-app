package fr.android.steam.services

import fr.android.steam.models.Game
import fr.android.steam.models.GameReview
import org.json.JSONArray
import org.json.JSONObject

class GameParser {

    companion object {
        fun parseJSONGames(jsonGames: JSONArray): List<Game> {
            val games: MutableList<Game> = mutableListOf()
            (0 until jsonGames.length()).forEach { i ->
                val currentGame: JSONObject = jsonGames.get(i) as JSONObject
                games.add(
                    Game(
                    currentGame.getString("id"),
                    currentGame.getString("name"),
                    currentGame.getString("description"),
                    currentGame.getString("publisher"),
                    currentGame.getString("price"),
                    currentGame.getString("mini_image"),
                    currentGame.getString("bg_image"),
                    currentGame.getString("cover_image"))
                )
            }
            return games
        }

        fun parseJSONGameReviews(jsonReviews: JSONArray): List<GameReview> {
            val reviews: MutableList<GameReview> = mutableListOf()
            (0 until jsonReviews.length()).forEach { i ->
                val currentGame: JSONObject = jsonReviews.get(i) as JSONObject
                reviews.add(
                    GameReview(
                        currentGame.getString("username"),
                        currentGame.getBoolean("good_grade"),
                        currentGame.getString("text"))
                )
            }
            return reviews
        }
    }

}