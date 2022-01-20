package com.example.coroutinesdemo

import android.content.ClipData
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class CustomAdapter(private val mList: List<repositories>):RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private lateinit var mListener:onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    class ViewHolder(ItemView:View, listener:onItemClickListener):RecyclerView.ViewHolder(ItemView) {
        val repoName:TextView=itemView.findViewById(R.id.repo_name)
        val starCount:TextView=itemView.findViewById(R.id.star_count)
        val desc:TextView=itemView.findViewById(R.id.description)
        val lang:TextView=itemView.findViewById(R.id.language)
        val fork:TextView=itemView.findViewById(R.id.fork_count)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

    }
    fun setOnItemClickListener(listener: onItemClickListener){
        mListener=listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.card_view_design,parent,false)

        return ViewHolder(view,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel=mList[position]
        holder.repoName.text=ItemsViewModel.name
        holder.starCount.text=ItemsViewModel.stargazers_count
        holder.desc.text=ItemsViewModel.description
        holder.lang.text=ItemsViewModel.language
        holder.fork.text=ItemsViewModel.forks

    }

    override fun getItemCount(): Int {
        return mList.size
    }

}