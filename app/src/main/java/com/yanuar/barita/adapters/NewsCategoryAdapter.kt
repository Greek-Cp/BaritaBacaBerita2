package com.yanuar.barita.adapters

import NewsAdapter.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.yanuar.barita.R

class NewsCategoryAdapter(private val nameCategory: List<String>, private val onItemClick: (String) -> Unit) : RecyclerView.Adapter<NewsCategoryAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: String, isSelected: Boolean){
            val textView = itemView.findViewById<TextView>(R.id.categoryName)
            val cardView = itemView.findViewById<MaterialCardView>(R.id.id_category_card)

            textView.text = item

            // Set the card background color based on the selection state
            if (isSelected) {
                cardView.setCardBackgroundColor(itemView.context.getColor(R.color.black)) // Define selected_card_color in your colors.xml
                textView.setTextColor(itemView.context.getColor(R.color.white)
                )
            } else {
                cardView.setCardBackgroundColor(itemView.context.getColor(R.color.white)) // Define default_card_color in your colors.xml
                textView.setTextColor(itemView.context.getColor(R.color.black))

            }

            textView.setOnClickListener {
                onItemClick(item)
                if (selectedPosition != adapterPosition) {
                    notifyItemChanged(selectedPosition)
                    selectedPosition = adapterPosition
                    notifyItemChanged(selectedPosition)
                }
            }
        }
    }

    var selectedPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = nameCategory[position]
        val isSelected = position == selectedPosition
        holder.bind(category, isSelected)
    }

    override fun getItemCount(): Int = nameCategory.size
}

