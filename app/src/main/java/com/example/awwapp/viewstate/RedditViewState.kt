package com.example.awwapp.viewstate

import com.example.awwapp.model.Post

sealed class RedditViewState {
    object LoadingState : RedditViewState()
    data class SuccessfulState(val redditList: Post) : RedditViewState()
    data class FailState(val message: String) : RedditViewState()
    data class ErrorState(val error: Throwable) : RedditViewState()

}