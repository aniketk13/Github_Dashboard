package com.example.coroutinesdemo

import android.database.Observable
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface APIService {


    @GET("users/{user}")
    suspend fun getUser(
        @Path("user") userid:String):Response<SimpleJSONModel>

    @GET("users/{user}/repos")
    suspend fun getrepos(@HeaderMap headers: Map<String,String>,@Path("user") userid: String): Response<List<repositories>>
}