package fr.android.steam.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.android.steam.R
import fr.android.steam.models.GameReview


class GameReviewAdapter(
    private val reviews: List<GameReview>
) : RecyclerView.Adapter<GameReviewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.review_adapter, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val review = reviews[position]

        holder.name.text = review.username
        holder.review.text = review.text

        if (!review.good_grade) {
           holder.good_grade.setImageResource(R.drawable.thumb_down)
        }
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val name: TextView = itemView.findViewById(R.id.review_name_label)
        val good_grade: ImageView = itemView.findViewById(R.id.review_grade_icon)
        val review: TextView = itemView.findViewById(R.id.review_text_label)
    }
}
