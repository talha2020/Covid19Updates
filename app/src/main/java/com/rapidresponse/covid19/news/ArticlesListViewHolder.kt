package com.rapidresponse.covid19.news

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.prof.rssparser.Article
import com.rapidresponse.covid19.GenericAdapter
import com.rapidresponse.covid19.R

class ArticlesListViewHolder (itemView: View,
                              val onItemClick: (Article) -> Unit): RecyclerView.ViewHolder(itemView),
    GenericAdapter.Binder<Article> {

    private var articleItemView = itemView.findViewById<CardView>(R.id.articleItemView)
    private var articlesTitleTv = itemView.findViewById<TextView>(R.id.articlesTitleTv)
    private var articleDateTv = itemView.findViewById<TextView>(R.id.articleDateTv)

    override fun bind(data: Article) {
        articlesTitleTv.text = data.title
        articleDateTv.text = data.pubDate
        articleItemView.setOnClickListener { onItemClick(data) }
    }

}