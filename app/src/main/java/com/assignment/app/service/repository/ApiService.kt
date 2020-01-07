package com.assignment.app.service.repository

import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    var BASE_URL: String
        get() = "https:mock-api-mobile.dev.lalamove.com"
        set(value) = TODO()

    @GET("/v2/deliveries")
    fun getDeliveryList(@Query("offset") offset: Int, @Query("limit") limit: Int)
}