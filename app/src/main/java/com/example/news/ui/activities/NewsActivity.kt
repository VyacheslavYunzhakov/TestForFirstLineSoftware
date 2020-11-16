package com.example.news.ui.activities

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.R
import com.example.news.model.NewsItem
import com.example.news.ui.adapters.NewsListAdapter
import com.example.news.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.activity_list_news.*
import kotlinx.coroutines.*


class NewsActivity : AppCompatActivity(), NewsListAdapter.OnItemClickListener {

    var onItemClickListener: NewsListAdapter.OnItemClickListener = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_news)

        val newsViewModel : NewsViewModel by lazy { ViewModelProvider(this).get(NewsViewModel::class.java) }

        newsList.layoutManager = LinearLayoutManager(this)

        swipeRefresh?.setOnRefreshListener {

            checkNetworkAndRetrieveNews(newsViewModel)
            swipeRefresh?.isRefreshing = false
        }
        checkNetworkAndRetrieveNews(newsViewModel)
    }

    private fun checkNetworkAndRetrieveNews(newsViewModel: NewsViewModel) {
        if (isNetworkConnected()) {
            retrieveNews(true,newsViewModel)
        } else {
            retrieveNews(false,newsViewModel)
            AlertDialog.Builder(this).setTitle("No Internet Connection")
                    .setMessage("Please check your internet connection and try again")
                    .setPositiveButton(android.R.string.ok) { _, _ -> }
                    .setIcon(android.R.drawable.ic_dialog_alert).show()
        }
    }

    private fun retrieveNews(internetConnection: Boolean,newsViewModel: NewsViewModel) {
        val mainActivityJob = Job()

        val errorHandler = CoroutineExceptionHandler { _, exception ->
            AlertDialog.Builder(this).setTitle("Error")
                    .setMessage(exception.message)
                    .setPositiveButton(android.R.string.ok) { _, _ -> }
                    .setIcon(android.R.drawable.ic_dialog_alert).show()
        }

        val coroutineScope = CoroutineScope(mainActivityJob + Dispatchers.Main)
        coroutineScope.launch(errorHandler) {
            if(internetConnection) {
                newsViewModel.init()
            }
            newsList.adapter = NewsListAdapter(onItemClickListener)
            newsViewModel.newsList.observe(getOwner(), Observer {
                it?.let{
                    (newsList.adapter as NewsListAdapter).refreshNews(it)
                }
            })
        }
    }
    private fun getOwner(): NewsActivity{
        return this
    }

    override fun onItemClick(view: View, newsItem: NewsItem) {
        startActivity(NewsDetailsActivity.navigate(this, newsItem))
    }
    private fun isNetworkConnected(): Boolean {
        //1
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        //2
        val activeNetwork = connectivityManager.activeNetwork
        //3
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        //4
        return networkCapabilities != null &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}


