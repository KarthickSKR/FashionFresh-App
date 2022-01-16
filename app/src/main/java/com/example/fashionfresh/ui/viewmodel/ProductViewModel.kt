package com.example.fashionfresh.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionfresh.ProductRepository.ProductRepository
import com.example.fashionfresh.data.model.ProductListModel
import com.example.fashionfresh.data.model.ProductsItem
import com.example.fashionfresh.data.roomdb.ProductsRoom
import com.example.fashionfresh.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {
    private val products = MutableLiveData<Resource<ProductListModel>>()
    val prdcts: LiveData<Resource<ProductListModel>>
        get() = products

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            try {
                products.postValue(Resource.loading(null))
                productRepository.getProducts().let {
                    if (it.isSuccessful) {
                        products.postValue(Resource.success(it.body()))
                        println("response: ${it.body()}")
                    } else products.postValue(Resource.error(it.errorBody().toString(), null))
                }
            }
           catch (e:Exception){
               e.printStackTrace()
           }
        }

    }

    fun insert(product: ProductsRoom) {
        CoroutineScope(Dispatchers.IO).launch {
            productRepository.insertProduct(product)
        }
    }

    fun updateProduct(productid: String, quantity: Int){
        productRepository.updateProduct(productid,quantity)
    }

    fun getQuantity(product: ProductsItem):Int{
        var i = 0
        if (productRepository.getQuantity(product.product_id)>0){
            i = productRepository.getQuantity(product.product_id)
        }
        return i
    }

    fun deleteProduct(productid: String){
        CoroutineScope(Dispatchers.IO).launch{
            productRepository.deleteProduct(productid)
        }

    }

}