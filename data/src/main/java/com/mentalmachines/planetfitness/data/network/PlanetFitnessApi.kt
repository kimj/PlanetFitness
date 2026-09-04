package com.mentalmachines.planetfitness.data.network

import com.mentalmachines.planetfitness.data.Program
import retrofit2.http.GET
import retrofit2.http.Path

interface PlanetFitnessApi {
    @GET("programs")
    suspend fun getPrograms(): List<Program>

    @GET("programs/{id}")
    suspend fun getProgramDetails(@Path("id") id: String): Program
}
