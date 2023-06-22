package com.example.testovoe15

import com.google.gson.annotations.SerializedName

data class Advice(
    @SerializedName("title") val title: String,
    @SerializedName("text") val text: String
)
