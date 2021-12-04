package com.example.awwapp.presenter

import com.example.awwapp.interactor.RedditInteractor
import com.example.awwapp.view.RedditView
import com.example.awwapp.viewstate.RedditViewState
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RedditPresenter : MviBasePresenter<RedditView, RedditViewState>() {
    override fun bindIntents() {
        val loadRedditData = intent(RedditView::fetchRedditList)
            .switchMap { RedditInteractor.getPosts(it) }
            .onErrorReturn { RedditViewState.ErrorState(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        val searchRedditData = intent(RedditView::onSearchIntent)
            .switchMap { RedditInteractor.getSearchPosts(it) }
            .onErrorReturn { RedditViewState.ErrorState(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        val allIntent = Observable.mergeArray(loadRedditData, searchRedditData)

        val stateReducer = { _: RedditViewState, changeState: RedditViewState -> changeState }
        val scanObservable = allIntent.scan(RedditViewState.LoadingState, stateReducer)
        subscribeViewState(scanObservable, RedditView::render)
    }
}