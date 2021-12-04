package com.example.awwapp.model

import com.google.gson.annotations.SerializedName

data class Children (@SerializedName("kind") val kind : String,
                     @SerializedName("data") var data : DataX)