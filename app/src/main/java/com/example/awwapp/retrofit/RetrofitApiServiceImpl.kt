package com.example.awwapp.retrofit

import com.example.awwapp.model.Post
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface RetrofitApiServiceImpl {

    @GET("/r/aww/top.json")
    fun getPosts(
        @Query("t") t: String,
        @Query("limit") limit: Int,
        @Query("after") after: String
    ): Observable<Post>

    @GET("/r/aww/search.json")
    fun getPostSearch(
        @Query("q") q: String,
        @Query("limit") limit: Int,
        @Query("after") after: String?,
    ): Observable<Post>
}
