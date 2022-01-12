package com.example.fashionfresh.data.remote

import com.example.fashionfresh.data.model.ProductListModel
import com.example.fashionfresh.utils.Constants
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET(Constants.RANDOM_URL)
    suspend fun getProducts() : Response<ProductListModel>
}