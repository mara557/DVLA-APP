package com.mara.dvlavehicleinformation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mara.dvlavehicleinformation.R
import com.mara.dvlavehicleinformation.models.VehicleComment

class CommentsAdapter(private val comments: List<VehicleComment>) :
    RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>() {

    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val commentText: TextView = itemView.findViewById(R.id.commentTextView)
        val commentType: TextView = itemView.findViewById(R.id.commentTypeTextView)
        val dangerousIndicator: ImageView = itemView.findViewById(R.id.dangerousIndicatorImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments[position]
        holder.commentText.text = comment.text
        holder.commentType.text = "Type: ${comment.type}"
        holder.dangerousIndicator.visibility = if (comment.dangerous) View.VISIBLE else View.GONE
    }

    override fun getItemCount(): Int = comments.size
}
