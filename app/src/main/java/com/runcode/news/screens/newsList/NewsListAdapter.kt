package com.runcode.news.screens.newsList

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.runcode.news.R
import com.runcode.news.data.model.BreakingNews

class NewsListAdapter(val context: Context, private var data: List<BreakingNews>) :
    RecyclerView.Adapter<NewsListAdapter.NewsListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListViewHolder {
        val view: View = LayoutInflater.from(context).inflate(
            R.layout.item_news_list, parent, false
        )
        return NewsListViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<BreakingNews>) {
        this.data = data
        notifyDataSetChanged()
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
        val item = data[position]
        holder.title.text = item.title
        holder.date.text = item.publishedAt
        Glide
            .with(context)
            .load(item.urlToImage)
            .placeholder(R.drawable.placeholder)
            .into(holder.img)
    }

    override fun getItemCount(): Int =
        data.size


    class NewsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.img_list_item)
        val title = itemView.findViewById<TextView>(R.id.title_news_item_list)
        val date = itemView.findViewById<TextView>(R.id.date_published)

    }
    interface OnNewsItemListener {
        fun onItemClickListener(breakingNews: BreakingNews)
    }
}