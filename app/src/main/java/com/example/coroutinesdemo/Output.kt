package com.example.coroutinesdemo

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coroutinesdemo.databinding.ActivityOutputBinding
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Output : AppCompatActivity() {
    private lateinit var binding: ActivityOutputBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityOutputBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val bundle: Bundle? =intent.extras
        val userid= bundle?.getString("id")
        Log.i("response", userid.toString())
        getMethod(userid.toString())
    }

    fun getMethod(userid:String) {

        // Create Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        // Create Service
        val service = retrofit.create(APIService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            /*
             * For @Query: You need to replace the following line with val response = service.getEmployees(2)
             * For @Path: You need to replace the following line with val response = service.getEmployee(53)
             */
            Log.i("id",userid)
            // Do the GET request and get response
            val response = service.getUser(userid)
            val repos=service.getrepos(getHeaderMap(),userid)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val ans=response.body()
                    if (ans != null) {
                        binding.id.text=ans.userid
                        binding.username.text=ans.username
                        Picasso.get().load(ans.avatarUrl).into(binding.imageView)
                        binding.bio.text=ans.bio
                        binding.followerCount.text=ans.followers + " Followers"
                        binding.followingCount.text=ans.following + " Following"
                        binding.twitterUser.text="@"+ ans.twitter
                        binding.repo.text=ans.repo
                        binding.twitterUser.setOnClickListener {
                            openUrl("https://twitter.com/${ans.twitter}")
                        }
                        binding.followerCount.setOnClickListener {
                            openUrl("https://github.com/${ans.userid}/?tab=followers")
                        }
                        binding.followingCount.setOnClickListener {
                            openUrl("https://github.com/${ans.userid}/?tab=following")
                        }
                        binding.repository.setOnClickListener {
                            openUrl("https://github.com/${ans.userid}/?tab=repositories")
                        }
                        binding.button2.setOnClickListener {
                            openUrl("https://github.com/${ans.userid}")
                        }
                        Log.i("repos",repos.body().toString())
                        for( i in repos.body()!!.indices){
                            Log.i("repos",repos.body()!![i].name.toString())
                            Log.i("repos",repos.body()!![i].stargazers_count.toString())
                            if(repos.body()!![i].description!=null)
                                Log.i("repos",repos.body()!![i].description.toString())
                        }
                        val recyclerView=binding.recyclerView
                        recyclerView.layoutManager=LinearLayoutManager(this@Output,LinearLayoutManager.HORIZONTAL,false)
                        val adapter=CustomAdapter(repos.body()!!)
                        recyclerView.adapter=adapter
                        adapter.setOnItemClickListener(object:CustomAdapter.onItemClickListener{
                            override fun onItemClick(position: Int) {
                                openUrl(repos.body()!![position].html_url)
                            }

                        })
                    }

                } else {
                    Log.e("RETROFIT_ERROR", response.code().toString())
                }



                }
            }
        }
    private fun getHeaderMap():Map<String,String>{
        val headerMap= mutableMapOf<String,String>()
        headerMap["Authorization"]="Basic YW5pa2V0azEzOmdocF9rZGpPbWxnTVdmcjB5b2dHY3Q0NXNCOEF6ZUVzeVMzbmNXUnI="
        return headerMap
    }

    fun openUrl(url:String){
        val intent=Intent(Intent.ACTION_VIEW)
        intent.data=Uri.parse(url)
        startActivity(intent)
    }




}