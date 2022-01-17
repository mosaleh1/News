package com.runcode.news.screens.custom_search

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.runcode.news.R
import com.runcode.news.data.model.BreakingNews

class CustomSearchAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BreakingNews>() {

        override fun areItemsTheSame(oldItem: BreakingNews, newItem: BreakingNews): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: BreakingNews, newItem: BreakingNews): Boolean {
            return (oldItem.articleUrl == newItem.articleUrl &&
                    oldItem.author == newItem.author &&
                    oldItem.isFavorite == newItem.isFavorite &&
                    oldItem.content == newItem.content &&
                    oldItem.name == newItem.name &&
                    oldItem.description == newItem.description &&
                    oldItem.publishedAt == newItem.publishedAt &&
                    oldItem.topic == newItem.topic &&
                    oldItem.urlToImage == newItem.urlToImage &&
                    oldItem.title == newItem.title &&
                    oldItem.id == oldItem.id)
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return CustomSearchViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_news_list,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CustomSearchViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<BreakingNews>) {
        differ.submitList(list)
        notifyDataSetChanged()
    }

    class CustomSearchViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: BreakingNews): Any = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            val title: TextView = itemView.findViewById(R.id.title_news_item_list)
            val date: TextView = itemView.findViewById(R.id.date_published)
            val image: ImageView = itemView.findViewById(R.id.img_list_item)

            title.text = item.title
            date.text = item.publishedAt
            Glide.with(context)
                .load(item.urlToImage)
                .error(R.drawable.news_logo_)
                .placeholder(R.drawable.placeholder)
                .into(image)
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: BreakingNews)
    }
}