package fr.android.steam

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.android.steam.models.Game

class GameAdapter(private val context: Context, private val games: List<Game>) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.game_adapter, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gamesView = games[position]

        // sets the text to the textview from our itemHolder class
        holder.name.text = gamesView.name
        holder.publisher.text = gamesView.publisher
        holder.price.text = gamesView.price

        // sets the image to the imageview from our itemHolder class
        Glide.with(context)
            .load(gamesView.mini_image)
            .centerCrop()
            .into(holder.mini_image);
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return games.size
    }

    // Holds he views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val name: TextView = itemView.findViewById(R.id.game_name_label)
        val publisher: TextView = itemView.findViewById(R.id.game_publisher_label)
        val price: TextView = itemView.findViewById(R.id.game_price_label)
        val mini_image: ImageView = itemView.findViewById(R.id.game_icon_img)
    }
}
