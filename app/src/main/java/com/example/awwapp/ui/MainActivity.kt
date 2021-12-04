package com.example.awwapp.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.awwapp.Constant.Companion.REDDIT_LIST_DATA
import com.example.awwapp.adapter.RedditPostAdapter
import com.example.awwapp.databinding.ActivityMainBinding
import com.example.awwapp.model.Children
import com.example.awwapp.presenter.RedditPresenter
import com.example.awwapp.view.RedditView
import com.example.awwapp.viewstate.RedditViewState
import com.hannesdorfmann.mosby3.mvi.MviActivity
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MviActivity<RedditView, RedditPresenter>(), RedditView {

    private lateinit var databinding: ActivityMainBinding

    private var filterKey: String = ""

    companion object {
        val publishSubject: PublishSubject<String> = PublishSubject.create()
        private val OnDataFilteredSubject = PublishSubject.create<String>()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        databinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(databinding.root)

        databinding.btnSearch.setOnClickListener {
            handleSearch()
        }
    }


    override fun onResume() {
        super.onResume()
        requestRedditData(REDDIT_LIST_DATA)
    }

    override val onSearchIntent: PublishSubject<String> = OnDataFilteredSubject

    override val fetchRedditList = publishSubject


    override fun render(state: RedditViewState) {
        when (state) {

            is RedditViewState.LoadingState -> renderLoadingState()
            is RedditViewState.SuccessfulState -> displayPostList(state.redditList.data.children)
            is RedditViewState.ErrorState -> errorMessage(state.error.message.toString())
            is RedditViewState.FailState -> errorMessage(state.message.toString())
        }
    }

    private fun renderLoadingState() {
        with(databinding) {
            loadingIndicators.visibility = VISIBLE
            postRecyclerview.visibility = GONE
            editTextSearch.visibility = GONE
            btnSearch.visibility = GONE
        }
    }

    private fun errorMessage(error: String) {
        with(databinding) {
            loadingIndicators.visibility = GONE
            postRecyclerview.visibility = GONE
            editTextSearch.visibility = GONE
            btnSearch.visibility = GONE
        }
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle(error)
        }.create().show()
    }

    override fun createPresenter(): RedditPresenter = RedditPresenter()

    private fun requestRedditData(limit: String) {
        fetchRedditList.onNext(limit)
    }

    private fun displayPostList(childrenList: List<Children>) {
        with(databinding) {
            loadingIndicators.visibility = View.GONE
            postRecyclerview.visibility = View.VISIBLE
            editTextSearch.visibility = VISIBLE
            btnSearch.visibility = VISIBLE
            postRecyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
            var postRecycleViewAdapter = RedditPostAdapter(childrenList)
            postRecyclerview.adapter = postRecycleViewAdapter
        }
    }

    private fun handleSearch() {
        if (filterKey.isNotEmpty()) {
            filterKey = databinding.editTextSearch.text.toString()
            OnDataFilteredSubject.onNext(filterKey)
        } else {
            requestRedditData(REDDIT_LIST_DATA)
        }
    }
}
