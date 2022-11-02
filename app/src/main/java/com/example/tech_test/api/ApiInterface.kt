package com.example.tech_test.api

import com.example.tech_test.model.Space_ModelItem
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET(ApiRes.END_POINT)
    suspend fun getData(): Response<ArrayList<Space_ModelItem>>
}