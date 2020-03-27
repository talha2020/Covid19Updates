package com.rapidresponse.covid19.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prof.rssparser.Article
import com.rapidresponse.covid19.*
import com.rapidresponse.covid19.data.UIResponse
import kotlinx.android.synthetic.main.fragment_home.progressBar
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : Fragment() {

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = activity?.run {
            ViewModelProvider(this, MainViewModelFactory())
                .get(MainActivityViewModel::class.java)
        } ?: throw Exception(
            getString(R.string.invalid_activity)
        )

        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.newsLiveData.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is UIResponse.Loading -> {
                    progressBar.show()
                }
                is UIResponse.Data<List<Article>> -> {
                    progressBar.setGone()
                    showArticlesList(response.data)
                }
                is UIResponse.Error -> {
                    progressBar.setGone()
                    onError(response)
                }
            }
        })

    }

    private fun showArticlesList(articles: List<Article>) {
        val adapter = object : GenericAdapter<Article>(articles) {
            override fun getLayoutId(position: Int, obj: Article): Int {
                return R.layout.articles_list_item
            }

            override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
                return ArticlesListViewHolder(view, onItemClick = {
                    launchArticleDetailActivity(it)
                })
            }
        }
        newsRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        newsRv.adapter = adapter
    }

    private fun launchArticleDetailActivity(article: Article) {

    }

}