package com.example.tech_test.app.detail_fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tech_test.R
import com.example.tech_test.app.MainViewModel
import com.example.tech_test.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment: Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        val viewModel by viewModels<MainViewModel>()

        binding = FragmentDetailBinding.bind(view)

        val myData = requireArguments().get("article") as ArrayList<String>

        Glide.with(requireActivity()).load(myData[0])
            .apply(RequestOptions().placeholder(R.drawable.ic_launcher_background)).into(binding.imageView)

        binding.title.text = myData[1]
        binding.summ.text = myData[2]
    }
}