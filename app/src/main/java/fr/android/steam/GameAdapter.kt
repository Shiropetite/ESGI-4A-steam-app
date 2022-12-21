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

class GameAdapter(
    private val context: Context,
    private val games: List<Game>
) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.game_adapter, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gamesView = games[position]

        holder.name.text = gamesView.name
        holder.publisher.text = gamesView.publisher
        holder.price.text = gamesView.price

        Glide.with(context)
            .load(gamesView.mini_image)
            .centerCrop()
            .into(holder.mini_image);
    }

    override fun getItemCount(): Int {
        return games.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val name: TextView = itemView.findViewById(R.id.game_name_label)
        val publisher: TextView = itemView.findViewById(R.id.game_publisher_label)
        val price: TextView = itemView.findViewById(R.id.game_price_label)
        val mini_image: ImageView = itemView.findViewById(R.id.game_icon_img)
    }
}
