package fr.android.steam.models

data class ApplicationUser(
    val username: String,
    val email: String,
    val password: String,
    val likedGames: List<Game>,
    val wishListedGames: List<Game>
) {

}
