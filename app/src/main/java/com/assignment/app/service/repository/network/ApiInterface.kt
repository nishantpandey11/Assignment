package com.assignment.app.service.repository.network

import com.assignment.app.service.model.Delivery
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {

    @GET("/v2/deliveries")
    fun getDeliveryList(@Query("offset") offset: Int, @Query("limit") limit: Int): Observable<List<Delivery>>
}