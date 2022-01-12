package com.example.fashionfresh.ui.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fashionfresh.R
import com.example.fashionfresh.data.model.ProductListModel
import com.example.fashionfresh.data.model.ProductsItem
import com.example.fashionfresh.data.roomdb.ProductsRoom
import com.example.fashionfresh.databinding.FragmentProductListBinding
import com.example.fashionfresh.ui.view.adapter.ProductAdapter
import com.example.fashionfresh.ui.viewmodel.ProductViewModel
import com.example.fashionfresh.utils.Resource
import com.example.fashionfresh.utils.Status
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProductListFragment : Fragment(R.layout.fragment_product_list) {

    private val productViewModel: ProductViewModel by viewModels()
    lateinit var productAdapter: ProductAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentProductListBinding.bind(view)
        setupObserver(binding)
    }

    private fun setupObserver(binding: FragmentProductListBinding) {
        productViewModel.prdcts.observe(this, Observer { response ->
            response.let {
                when (it.status) {
                    Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        binding.recyclerView.visibility = View.VISIBLE
                        initRecyclerView(response, binding)
                        println("response: ${response.data?.products!!}")
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        //Handle Error
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

        })
    }

    private fun initRecyclerView(
        response: Resource<ProductListModel>?,
        binding: FragmentProductListBinding
    ) {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            val gridLayoutManager = GridLayoutManager(context, 2)
            binding.recyclerView.layoutManager = gridLayoutManager
            binding.recyclerView.adapter = adapter
            adapter = response!!.data?.products.let { it1 ->
                ProductAdapter(it1!!,productViewModel)
            }
            productAdapter = adapter as ProductAdapter
            productAdapter.setWhenClickListener(object : ProductAdapter.OnItemsClickListener {
                override fun onProductItemClicked(product: ProductsItem?) {
                    val productsRoom = ProductsRoom(
                        product!!.id,
                        product.product_id,
                        product.name,
                        product.image,
                        product.price,
                        product.quantity
                    )
                    productViewModel.insert(productsRoom)
                    println("productRoom: $productsRoom")
                }
            })

        }
    }

}