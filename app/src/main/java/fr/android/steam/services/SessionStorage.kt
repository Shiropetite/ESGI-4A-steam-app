package fr.android.steam.services

import fr.android.steam.models.ApplicationUser
import fr.android.steam.models.Game

class SessionStorage {
    companion object {
        private lateinit var currentUser: ApplicationUser
        private var games: List<Game> = listOf()

        fun setGames(games: List<Game>) {
            this.games = games
        }

        fun getGames(): List<Game> {
            return this.games
        }

        fun setCurrentUser(currentUser: ApplicationUser) {
            this.currentUser = currentUser
        }

        fun getCurrentUser(): ApplicationUser {
            return this.currentUser
        }
    }
}