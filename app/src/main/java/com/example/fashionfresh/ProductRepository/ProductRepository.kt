package com.example.fashionfresh.ProductRepository

import com.example.fashionfresh.data.model.ProductListModel
import com.example.fashionfresh.data.remote.ApiService
import com.example.fashionfresh.data.roomdb.ProductDao
import com.example.fashionfresh.data.roomdb.ProductsRoom
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ProductRepository @Inject constructor(
    private val apiService: ApiService,
    private val productDao: ProductDao
) {
    suspend fun getProducts(): Response<ProductListModel> = apiService.getProducts()

    fun insertProduct(productsRoom: ProductsRoom) = productDao.insertData(productsRoom)

    fun updateProduct(productid: String, quantity: Int) =
        productDao.productupdate(productid, quantity)

    fun getQuantity(productId: String) = productDao.itemcount(productId)

    fun deleteProduct(productId: String) = productDao.deleteProduct(productId)

    fun getProductsFromRoom() = productDao.getProducts()

    fun getAllProduct() = productDao.getallFromRoom()

}