package com.runcode.news.screens.home

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.runcode.news.R
import com.runcode.news.data.model.BreakingNews
import java.util.ArrayList

private const val TAG = "NewsAdapter"

class NewsAdapter(val context: Context, val data: List<BreakingNews> = listOf()) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    lateinit var clickListener: OnNewsItemListener

    lateinit var saveToFavClickListener: OnFavIconListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: ")
        if (data.isNotEmpty()) {
            holder.bind(data[position], position)
        }
    }


    override fun getItemCount(): Int {
        return data.size
    }

//    @SuppressLint("NotifyDataSetChanged")
//    fun setData(list: List<BreakingNews>) {
//        Log.d(TAG, "setData: ${list.size}")
//        data.clear()
//        data.addAll(list)
//        notifyDataSetChanged()
//    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val image: ImageView = itemView.findViewById(R.id.image_item)
        private val title: TextView = itemView.findViewById(R.id.news_item_title)
        private val author: TextView = itemView.findViewById(R.id.author_item)
        private val date: TextView = itemView.findViewById(R.id.date)
        private val iconSaveToFavorite: ImageView =
            itemView.findViewById(R.id.save_to_fav_icon_home)

        fun bind(breakingNews: BreakingNews, position: Int) {
            Log.d(TAG, "bind: called")
            title.text = breakingNews.title
            author.text = breakingNews.author
            iconSaveToFavorite.isSelected = breakingNews.isFavorite
            date.text = getFormattedDate(breakingNews.publishedAt ?: "")

            itemView.setOnClickListener {
                clickListener.onItemClickListener(breakingNews)
            }
            iconSaveToFavorite.setOnClickListener {
                iconSaveToFavorite.isSelected = !iconSaveToFavorite.isSelected
                breakingNews.isFavorite = iconSaveToFavorite.isSelected
                saveToFavClickListener.onFavIconClickedListener(
                    breakingNews,
                    breakingNews.isFavorite
                )
                data[position].isFavorite = iconSaveToFavorite.isSelected
            }

            Glide
                .with(context)
                .load(breakingNews.urlToImage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.placeholder)
                .into(image)
        }

    }

    private fun getFormattedDate(dateParam: String): String {
        val year = dateParam.substring(0, 4)
        val month = dateParam.substring(5, 7)
        val day = dateParam.substring(8, 10)
        return "$day/$month/$year"
    }

    interface OnNewsItemListener {
        fun onItemClickListener(breakingNews: BreakingNews)
    }

    interface OnFavIconListener {
        fun onFavIconClickedListener(breakingNews: BreakingNews, isSaveAction: Boolean)
    }

}