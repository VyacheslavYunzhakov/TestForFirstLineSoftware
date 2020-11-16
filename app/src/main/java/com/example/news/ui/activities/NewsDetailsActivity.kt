package com.example.news.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import com.example.news.R
import com.example.news.model.NewsItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_news_details.*
import kotlinx.android.synthetic.main.content_news_details.*


class NewsDetailsActivity: AppCompatActivity() {
    companion object {
        private const val EXTRA_NEWS = "extraNews"
        private const val IMAGE = "image"
        fun navigate(activity: Activity, newsItem: NewsItem):Intent
        {

            val intent = Intent(activity, NewsDetailsActivity::class.java)
            intent.putExtra(EXTRA_NEWS, newsItem)
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)

        val newsItem = intent.getSerializableExtra(EXTRA_NEWS) as NewsItem
        showNews(newsItem)
    }

    private fun showNews(newsItem: NewsItem) {

        toolbar_layout.title = newsItem.title
        toolbar_layout.setExpandedTitleColor(
                ContextCompat.getColor(
                        this,
                        android.R.color.transparent
                )
        )

        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        metrics.widthPixels

        image.layoutParams.width = metrics.widthPixels
        image.layoutParams.height = metrics.widthPixels * 340/640
        try {
        Picasso.get().load(
                newsItem.description.substring(
                        newsItem.description.indexOf("\"") + 1,
                        newsItem.description.indexOf("\"", newsItem.description.indexOf("\"") + 1)
                )
        ).into(image)
       }
        catch (e: Exception) {

        }
        textTitle.text =newsItem.title
        if (newsItem.author!="") {
            authorView.text = newsItem.author
        }
        publishDateView.text = newsItem.pubDate

        if (!newsItem.category?.isEmpty()!!){
            categoriesView.visibility = View.VISIBLE
            for (i in newsItem.category!!.indices){
                if (i < newsItem.category!!.size -1) {
                    categoriesView.append(newsItem.category!![i] + ", ")
                }
                else categoriesView.append(newsItem.category!![i])
            }
        }

        descriptionView.text = newsItem.description.substring(
                newsItem.description.indexOf(">") + 1)


    }
}