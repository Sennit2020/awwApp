package com.example.awwapp.view

import com.example.awwapp.viewstate.RedditViewState
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.subjects.PublishSubject

interface RedditView : MvpView {
    val onSearchIntent: PublishSubject<String>
    val fetchRedditList: PublishSubject<String>
    fun render(state: RedditViewState)
}