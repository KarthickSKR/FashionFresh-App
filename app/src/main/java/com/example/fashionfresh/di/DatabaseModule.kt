package com.example.fashionfresh.di

import android.app.Application
import androidx.room.Room
import com.example.fashionfresh.data.roomdb.ProductDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDataBase(appication: Application, callback: ProductDataBase.Callback) =
        Room.databaseBuilder(appication, ProductDataBase::class.java, "product_database")
            .fallbackToDestructiveMigration()
            .addCallback(callback).build()
    @Provides
    fun providesUserDao(productDataBase: ProductDataBase) =
        productDataBase.productDao()
}