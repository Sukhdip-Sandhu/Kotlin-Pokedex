package com.example.kotlinpokedex.Retrofit

import com.example.kotlinpokedex.Model.Pokedex
import io.reactivex.Observable
import retrofit2.http.GET

interface IPokemonList {
    @get:GET("pokedex.json")
    val listPokemon: Observable<Pokedex>
}