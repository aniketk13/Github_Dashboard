package com.example.coroutinesdemo

import com.google.gson.annotations.SerializedName

data class SimpleJSONModel(
    @SerializedName("login")
    var userid:String?,

    @SerializedName("name")
    var username:String?,

    @SerializedName("avatar_url")
    var avatarUrl:String?,

    @SerializedName("bio")
    val bio:String?,

    @SerializedName("followers")
    val followers:String?,

    @SerializedName("following")
    val following:String?,

    @SerializedName("twitter_username")
    val twitter:String?,

    @SerializedName("public_repos")
    val repo:String?
)
