package com.example.awwapp.interactor

import com.example.awwapp.Constant
import com.example.awwapp.model.Post
import com.example.awwapp.retrofit.RetrofitClientInstance
import com.example.awwapp.viewstate.RedditViewState
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

object RedditInteractor {

    private val apiCall = RetrofitClientInstance.buildService()

    fun getPosts(limit: String): Observable<RedditViewState> {

        return retriveRedditList(limit)
            .subscribeOn(Schedulers.io())
            .switchMap {
                if (it.kind.isNotEmpty()) {
                    return@switchMap Observable.just(RedditViewState.SuccessfulState(it))
                } else {
                    return@switchMap Observable.just(RedditViewState.ErrorState(Throwable()))
                }
            }.startWith(Observable.just(RedditViewState.LoadingState))
    }

    fun getSearchPosts(searchString: String): Observable<RedditViewState> {

        return searchRedditList(searchString)
            .subscribeOn(Schedulers.io())
            .switchMap {
                if (it.kind.isNotEmpty()) {
                    return@switchMap Observable.just(RedditViewState.SuccessfulState(it))
                } else {
                    return@switchMap Observable.just(RedditViewState.ErrorState(Throwable()))
                }
            }.startWith(Observable.just(RedditViewState.LoadingState))

    }

    private fun retriveRedditList(limit: String): Observable<Post> {
        return apiCall.getPosts(limit, Constant.LIMIT, Constant.AFTER);
    }

    private fun searchRedditList(searchString: String): Observable<Post> {
        return apiCall.getPostSearch(searchString, Constant.LIMIT, Constant.AFTER);
    }

}

