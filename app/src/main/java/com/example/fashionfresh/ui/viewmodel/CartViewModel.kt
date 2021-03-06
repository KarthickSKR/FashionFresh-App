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
    var totalPrice : MutableLiveData<String>? = MutableLiveData()

    fun getProducts() {
        CoroutineScope(Dispatchers.IO).launch {
            productList?.postValue(productRepository.getAllProduct())
            println("ProducList :"+productRepository.getAllProduct())
            getCartTotalPrice()
        }
    }

    fun updateProduct(productid: String, quantity: Int){
        productRepository.updateProduct(productid,quantity)
        getCartTotalPrice()
    }
    fun getQuantity(product: ProductsRoom):Int{
        var i = 0
        if (productRepository.getQuantity(product.productid)>0){
            i = productRepository.getQuantity(product.productid)
        }
        return i
    }

    fun getCartTotalPrice(): Double {
        val productList = productRepository.getAllProduct()
        var total = 0.0
        productList.forEach {
            val priceWithoutSymbol = it.price.replace("₹","")
            val price = priceWithoutSymbol.replace(",","")

            val productTotal = it.quantity * price.toDouble()
            total += productTotal
        }

        println("Total "+total)
        totalPrice?.postValue("₹ "+total)
        return total
    }


    fun deleteProduct(productid: String){
        CoroutineScope(Dispatchers.IO).launch{
            productRepository.deleteProduct(productid)
            getCartTotalPrice()
            getProducts()
        }

    }

}