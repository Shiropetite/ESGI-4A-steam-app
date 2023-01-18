package fr.android.steam.services

import fr.android.steam.models.ApplicationUser

class SessionService {
    companion object {
        private lateinit var currentUser: ApplicationUser

        fun setCurrentUser(currentUser: ApplicationUser) {
            this.currentUser = currentUser;
        }

        fun getCurrentUser(): ApplicationUser {
            return this.currentUser
        }
    }
}