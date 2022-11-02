package com.example.tech_test.app.main_fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tech_test.R
import com.example.tech_test.databinding.RecyclerCardBinding
import com.example.tech_test.model.Space_ModelItem

class MainAdapter(val insertedList: ArrayList<Space_ModelItem>, val context: Context): RecyclerView.Adapter<MainAdapter.RecyclerViewHolder>() {
    class RecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private lateinit var binding: RecyclerCardBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_card,parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {

        var curItem = insertedList[position]


        binding = RecyclerCardBinding.bind(holder.itemView)

        Glide.with(context).load(curItem.imageUrl)
            .apply(RequestOptions().placeholder(R.drawable.ic_launcher_background)).into(binding.imageView)

        binding.toptitle.text = curItem.newsSite
        binding.articleTitle.text = curItem.title

        //viewModel.delSpecific(curItem)





        holder.itemView.setOnClickListener(){

            val myList = arrayListOf<String>(curItem.imageUrl!!, curItem.title!!, curItem.summary!!)

            val bundle = Bundle()
            bundle.putStringArrayList("article",myList)

            it.findNavController().navigate(R.id.action_mainFragment_to_detailFragment, bundle)
        }


    }

    override fun getItemCount(): Int {

        return insertedList.size
    }
}