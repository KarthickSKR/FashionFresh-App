package com.example.fashionfresh.data.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.fashionfresh.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [ProductsRoom::class], version = 2, exportSchema = false)
abstract class ProductDataBase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    class Callback @Inject constructor(
        private val productDb: Provider<ProductDataBase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val dao = productDb.get().productDao()
            applicationScope.launch {
            }
        }
    }

}