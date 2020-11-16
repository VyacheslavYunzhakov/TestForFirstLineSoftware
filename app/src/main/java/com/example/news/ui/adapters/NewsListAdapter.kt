package com.example.news.ui.adapters

import android.opengl.Visibility
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.model.Channel
import com.example.news.model.NewsItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_news_details.*
import kotlinx.android.synthetic.main.item_news.view.*

class NewsListAdapter( onItemClickListener: OnItemClickListener
): RecyclerView.Adapter<NewsListAdapter.ViewHolder>(){

    private var newsList= Channel(ArrayList())
    private val mOnItemClickListener = onItemClickListener

    private val mInternalListener =
            View.OnClickListener { view ->
                val newsItem = view.tag as NewsItem
                mOnItemClickListener.onItemClick(view, newsItem)
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return ViewHolder(view);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindNews(newsList.newsItems?.get(position))
        holder.itemView.setOnClickListener(mInternalListener)
        holder.itemView.tag = newsList.newsItems?.get(position)
    }

    override fun getItemCount(): Int = newsList.newsItems!!.size

    fun refreshNews(newsList: Channel?) {
        if (newsList != null) {
            this.newsList = newsList
        }
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindNews(newsItem: NewsItem?) {
            itemView.newsTitleView.text = newsItem?.title
            if (newsItem?.author!="") {
                itemView.newsAuthorView.visibility = View.VISIBLE
                itemView.newsAuthorView.text = newsItem?.author
            }
            itemView.newsPublishDateView.text = newsItem?.pubDate
            Picasso.get().load(
                newsItem?.description?.substring(
                    newsItem.description.indexOf("\"") + 1,
                    newsItem.description.indexOf("\"", newsItem.description.indexOf("\"") + 1)
                )
            ).into(itemView.image)
        }

    }
    interface OnItemClickListener {
        fun onItemClick(view: View, newsItem: NewsItem)
    }
}