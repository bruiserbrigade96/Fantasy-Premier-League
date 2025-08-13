package com.example.fplassistant.data.remote

import com.example.fplassistant.data.model.BootstrapStatic
import retrofit2.http.GET
import retrofit2.http.Path

interface FplApi {
    @GET("bootstrap-static/")
    suspend fun getBootstrapStatic(): BootstrapStatic

    @GET("fixtures/")
    suspend fun getFixtures(): List<Any>

    @GET("element-summary/{id}/")
    suspend fun getElementSummary(@Path("id") id: Int): Any

    companion object {
        const val BASE_URL = "https://fantasy.premierleague.com/api/"
    }
}