package com.example.fashionfresh.ui.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fashionfresh.R
import com.example.fashionfresh.data.roomdb.ProductsRoom
import com.example.fashionfresh.databinding.ItemCartBinding
import com.example.fashionfresh.ui.viewmodel.CartViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartAdapter(
    private val productList: List<ProductsRoom>,
    val cartViewModel: CartViewModel
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    inner class CartViewHolder(val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductsRoom) {
            var i = 0
            CoroutineScope(Dispatchers.IO).launch {
                if (cartViewModel.getQuantity(product) > 0) {
                    i = cartViewModel.getQuantity(product)
                }
            }
            binding.productName.text = product.productname
            binding.productPrice.text = product.price
            binding.qtycount.text = product.quantity.toString()
            Glide.with(itemView.context).load(product.productImage).into(binding.productImage)
            binding.plus.setOnClickListener {
                if (i >= 0) {
                    i++
                    CoroutineScope(Dispatchers.IO).launch {
                        cartViewModel.updateProduct(product.productid, i)
                    }
                    binding.qtycount.setText(""+i)
                }
            }
            binding.dec.setOnClickListener {
                if (i <= 1) {
//                    binding.qtyLayout.visibility = View.GONE
                    cartViewModel.deleteProduct(product.productid)
                } else {
                    i--
                    CoroutineScope(Dispatchers.IO).launch {
                        cartViewModel.updateProduct(product.productid, i)
                    }
                    binding.qtycount.setText(""+i)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemCartBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_cart, parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int {
        println("listsize : ${productList.size}")
        return productList.size
    }
}