package com.example.pokemonassesmenttask.core.network

import com.example.pokemonassesmenttask.pokemon.data.service.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.component.KoinComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiProvider : KoinComponent {

    private fun loggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    private fun httpClient() =
        OkHttpClient.Builder().apply {
            addInterceptor(loggingInterceptor())
        }.build()

    private val retrofit = Retrofit.Builder().apply {
        baseUrl("https://pokeapi.co/api/v2/")
        addConverterFactory(GsonConverterFactory.create())
        client(httpClient())
    }.build()

    val client : ApiService by lazy { retrofit.create(ApiService::class.java) }
}