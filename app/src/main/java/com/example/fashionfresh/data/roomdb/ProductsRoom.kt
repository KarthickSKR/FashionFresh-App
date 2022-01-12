package com.example.fashionfresh.data.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productlist_table")
data class ProductsRoom(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") var id: String,
    @ColumnInfo(name = "productid") val productid: String,
    @ColumnInfo(name = "productname") val productname: String,
    @ColumnInfo(name = "productImage") val productImage: String,
    @ColumnInfo(name = "price") val price: String,
    @ColumnInfo(name = "quantity") val quantity: Int
)
