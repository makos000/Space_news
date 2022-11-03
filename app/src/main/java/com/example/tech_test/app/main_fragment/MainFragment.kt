package com.example.tech_test.app.main_fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tech_test.R
import com.example.tech_test.app.MainViewModel
import com.example.tech_test.databinding.FragmentMainBinding
import com.example.tech_test.model.Space_ModelItem
import com.example.tech_test.util.CheckNetwork
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment: Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding

    private lateinit var adapter: MainAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        val viewModel by viewModels<MainViewModel>()

        val networkObj = CheckNetwork()

        binding = FragmentMainBinding.bind(view)

        val recycler = binding.recyclerV
        val layoutManager = LinearLayoutManager(context)
        recycler.layoutManager = layoutManager


        if (networkObj.isNetworkAvailable(requireActivity())){
            viewModel.getData()
            viewModel.data.observe(requireActivity()){
                adapter = MainAdapter(it,requireActivity())
                recycler.adapter = adapter
            }
        }
        else{
            viewModel.readDatabase()
            var list = arrayListOf<Space_ModelItem>()
            viewModel.readArticle.observe(requireActivity()){
                for (article in it){
                    list.add(article.spaceModel)
                }
                adapter = MainAdapter(list,requireActivity())
                recycler.adapter = adapter
            }
        }





    }
}