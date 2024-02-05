package com.example.onlineshopapp.data.api

import com.example.onlineshopapp.data.model.ItemResponse
import retrofit2.http.GET

interface ApiService {

    @GET("97e721a7-0a66-4cae-b445-83cc0bcf9010")
    suspend fun loadItems(): ItemResponse
}