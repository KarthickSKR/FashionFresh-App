package com.example.fashionfresh.data.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(productsRoom: ProductsRoom)

    @Query("SELECT * FROM productlist_table ORDER BY id ASC")
    fun getProducts(): LiveData<List<ProductsRoom>>

    @Query("DELETE FROM productlist_table WHERE productid = :prd_id")
    fun deleteProduct(prd_id: String)

    @Query("SELECT * FROM productlist_table")
    fun getallFromRoom() : List<ProductsRoom>

    @Query("SELECT quantity FROM productlist_table WHERE productid= :product_id")
    fun itemcount(product_id : String): Int

    @Query("UPDATE productlist_table SET quantity =:qty WHERE productid=:product_id")
    fun productupdate(product_id : String, qty : Int)

}