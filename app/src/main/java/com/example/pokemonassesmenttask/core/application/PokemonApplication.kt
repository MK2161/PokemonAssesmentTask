package com.example.pokemonassesmenttask.application

import android.app.Application
import com.example.pokemonassesmenttask.core.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class PokemonApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        configKoin()
    }

    private fun configKoin() {
        startKoin {
            androidContext(this@PokemonApplication)
            modules(AppModule.appModules())
        }
    }
}