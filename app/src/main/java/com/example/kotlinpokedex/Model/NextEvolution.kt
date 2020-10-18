package com.example.kotlinpokedex.Model


import com.google.gson.annotations.SerializedName

data class NextEvolution(
    @SerializedName("name")
    val name: String,
    @SerializedName("num")
    val num: String
)