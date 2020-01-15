package com.assignment.app.data.source.network

import com.assignment.app.data.model.Delivery
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {

    @GET("/v2/deliveries")
    fun getDeliveryList(@Query("offset") offset: Int, @Query("limit") limit: Int): Observable<List<Delivery>>
}