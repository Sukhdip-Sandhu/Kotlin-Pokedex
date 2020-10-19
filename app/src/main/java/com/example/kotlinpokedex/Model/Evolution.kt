package com.example.kotlinpokedex.Model


import com.google.gson.annotations.SerializedName

data class Evolution(
    @SerializedName("name")
    val name: String,
    @SerializedName("num")
    val num: String
)