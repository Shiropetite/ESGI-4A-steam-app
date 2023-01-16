package fr.android.steam.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.android.steam.R
import fr.android.steam.activities.GameDetailsActivity
import fr.android.steam.models.Game
import kotlinx.coroutines.NonDisposableHandle.parent


class GameAdapter(
    private val games: List<Game>
) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.game_adapter, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game = games[position]

        holder.name.text = game.name
        holder.publisher.text = game.publisher
        holder.price.text = game.price

        Glide.with(context)
            .load(game.mini_image)
            .centerCrop()
            .into(holder.mini_image)

        holder.details_link.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("_game", game)
            val i = Intent(context, GameDetailsActivity::class.java)
            i.putExtra("_bundle", bundle)
            startActivity(context, i, bundle)
        }
    }

    override fun getItemCount(): Int {
        return games.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val name: TextView = itemView.findViewById(R.id.game_name_label)
        val publisher: TextView = itemView.findViewById(R.id.game_publisher_label)
        val price: TextView = itemView.findViewById(R.id.game_price_label)
        val mini_image: ImageView = itemView.findViewById(R.id.game_icon_img)
        val details_link: AppCompatButton = itemView.findViewById(R.id.game_details_btn)
    }
}
