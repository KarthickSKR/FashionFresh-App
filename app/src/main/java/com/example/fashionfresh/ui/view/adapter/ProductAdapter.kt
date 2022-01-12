package com.example.fashionfresh.ui.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fashionfresh.R
import com.example.fashionfresh.data.model.ProductsItem
import com.example.fashionfresh.databinding.ProductViewBinding
import com.example.fashionfresh.ui.viewmodel.ProductViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductAdapter(
    private val productList: List<ProductsItem>,
    val productViewModel: ProductViewModel
) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var listener: OnItemsClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ProductViewBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.product_view, parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])

    }


    override fun getItemCount(): Int {
        return productList.size
    }

    fun setWhenClickListener(listener: OnItemsClickListener?) {
        this.listener = listener!!
    }

    interface OnItemsClickListener {
        fun onProductItemClicked(product: ProductsItem?)
    }

    inner class ProductViewHolder(private val binding: ProductViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currentProduct: ProductsItem) {
            var i = 0
            binding.product = currentProduct
            Glide.with(itemView.context).load(currentProduct.image).into(binding.productImage)
            CoroutineScope(Dispatchers.IO).launch {
                println("product qty ${productViewModel.getQuantity(currentProduct)}")
                if (productViewModel.getQuantity(currentProduct) > 0) {
                    i = productViewModel.getQuantity(currentProduct)
                }
            }
            binding.plus.setOnClickListener {
                if (i >= 0) {
                    i++
                    CoroutineScope(Dispatchers.IO).launch {
                        productViewModel.updateProduct(currentProduct.product_id, i)
                    }
                    binding.qtycount.setText(""+i)
                }
            }
            binding.dec.setOnClickListener {
                if (i <= 1) {
                    binding.add.visibility = View.VISIBLE
                    binding.qtyLayout.visibility = View.GONE
                    productViewModel.deleteProduct(currentProduct.product_id)
                } else {
                    i--
                    CoroutineScope(Dispatchers.IO).launch {
                        productViewModel.updateProduct(currentProduct.product_id, i)
                    }
                    binding.qtycount.setText(""+i)
                }
            }

            binding.add.setOnClickListener {
                binding.add.visibility = View.GONE
                binding.qtyLayout.visibility = View.VISIBLE
                println("product Id is ${currentProduct.product_id}")
                listener?.onProductItemClicked(currentProduct)
            }

        }
    }
}




