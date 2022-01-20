package com.example.coroutinesdemo

import com.google.gson.annotations.SerializedName

data class repositories(var name:String,
                        var stargazers_count:String,
                        var description:String?,
                        var language:String?,
                        var forks:String,
                        var html_url:String)

