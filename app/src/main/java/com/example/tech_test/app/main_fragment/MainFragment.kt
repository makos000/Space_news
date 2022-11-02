package com.example.tech_test.app.main_fragment

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tech_test.R
import com.example.tech_test.app.MainViewModel
import com.example.tech_test.databinding.FragmentMainBinding
import com.example.tech_test.model.Space_ModelItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment: Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding

    private lateinit var adapter: MainAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        val viewModel by viewModels<MainViewModel>()



        binding = FragmentMainBinding.bind(view)

        val recycler = binding.recyclerV
        val layoutManager = LinearLayoutManager(context)
        recycler.layoutManager = layoutManager


        if (isNetworkAvailable(requireActivity())){
            viewModel.getData()
            viewModel._data.observe(requireActivity()){
                adapter = MainAdapter(it,requireActivity())
                recycler.adapter = adapter
            }
        }
        else{
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

    private fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }
}