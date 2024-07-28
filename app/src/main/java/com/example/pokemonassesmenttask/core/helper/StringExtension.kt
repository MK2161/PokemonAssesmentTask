package com.example.pokemonassesmenttask.core.helper

    fun String.extractIdFromUrl() : String? {
        val regex = """.*/(\d+)/?$""".toRegex()
        val matchResult = regex.find(this)
        return matchResult?.groupValues?.get(1)
    }
