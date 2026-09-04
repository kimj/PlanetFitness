package com.mentalmachines.planetfitness.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkClient {
    private const val BASE_URL = "https://69fa8c1388a7af0ecca77ae5.mockapi.io/pf/" // Placeholder URL

    val api: PlanetFitnessApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PlanetFitnessApi::class.java)
    }
}
