package com.example.fashionfresh.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fashionfresh.ProductRepository.ProductRepository
import com.example.fashionfresh.data.roomdb.ProductsRoom
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(val productRepository: ProductRepository) : ViewModel() {

    var productList : MutableLiveData<List<ProductsRoom>>? = MutableLiveData()

    fun getProducts() {
        CoroutineScope(Dispatchers.IO).launch {
            productList?.postValue(productRepository.getAllProduct())
            println("ProducList :"+productRepository.getAllProduct())
        }
    }

    fun updateProduct(productid: String, quantity: Int){
        productRepository.updateProduct(productid,quantity)
    }
    fun getQuantity(product: ProductsRoom):Int{
        var i = 0
        if (productRepository.getQuantity(product.productid)>0){
            i = productRepository.getQuantity(product.productid)
        }
        return i
    }


    fun deleteProduct(productid: String){
        CoroutineScope(Dispatchers.IO).launch{
            productRepository.deleteProduct(productid)
        }

    }

}