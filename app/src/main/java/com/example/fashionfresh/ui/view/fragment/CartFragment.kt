package com.example.fashionfresh.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fashionfresh.R
import com.example.fashionfresh.databinding.FragmentCartBinding
import com.example.fashionfresh.ui.view.adapter.CartAdapter
import com.example.fashionfresh.ui.viewmodel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private val cartViewModel: CartViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCartBinding.inflate(inflater, container, false)
        val view = binding.root
        initUI(binding)
        return view
    }

    private fun initUI(binding: FragmentCartBinding) {
        cartViewModel.getProducts()
        cartViewModel.productList?.observe(this.viewLifecycleOwner, {
            val adapter = CartAdapter(it!!, cartViewModel)
            binding.cartRecyclerView.layoutManager = LinearLayoutManager(context)
            binding.cartRecyclerView.adapter = adapter
            println("cartProducts ${it}")
        })
        cartViewModel.totalPrice?.observe(this.viewLifecycleOwner,{
            binding.totalPrice.text = it
        })

    }


}