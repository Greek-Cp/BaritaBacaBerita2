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

class NewsCategoryAdapter(private val nameCategory: List<String>, private val onItemClick: (String) -> Unit) : RecyclerView.Adapter<NewsCategoryAdapter.CategoryViewHolder>() {

    var selectedPosition = RecyclerView.NO_POSITION


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = nameCategory[position]

        holder.bind(category, position == selectedPosition)
    }

    override fun getItemCount(): Int = nameCategory.size

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val categoryName: TextView = itemView.findViewById(R.id.categoryName)
        private val cardCategory: MaterialCardView = itemView.findViewById(R.id.id_category_card)

        fun bind(categoryName: String, isSelected: Boolean) {

        }
    }
}
