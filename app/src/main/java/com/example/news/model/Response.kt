package com.example.news.model

import android.graphics.Movie
import org.simpleframework.xml.*
import java.io.Serializable
import kotlin.jvm.Transient


@Root(name = "rss", strict = false)
class Feed @JvmOverloads constructor(@field:Element(name = "channel") var channel: Channel? = null): Serializable

@Root(name = "channel", strict = false)
class Channel @JvmOverloads constructor(
    @field:ElementList(inline = true, name = "item")
    var newsItems: MutableList<NewsItem>? = null
) : Serializable {
    fun add(newsItem: NewsItem) {
        newsItems?.add(newsItem)
    }
}

@Root(name = "item", strict = false)
class NewsItem @JvmOverloads constructor(
    @field:ElementList(inline = true, name = "category", entry = "category", required = false)
    var category: List<String>? = null,
    @field:Element(name = "pubDate")
    var pubDate: String = "",
    @field:Element(name = "title")
    var title: String = "",
    @field:Element(name = "link")
    var link: String = "",
    @field:Element(name = "description")
    var description: String = "",
    @field:Element(name = "author", required = false)
    var author: String = ""
): Serializable
