package com.example.fashionfresh.ui.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fashionfresh.R
import com.example.fashionfresh.databinding.ActivityProductListBinding
import com.example.fashionfresh.utils.ConnectionType
import com.example.fashionfresh.utils.NetworkMonitorUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListActivity : AppCompatActivity() {
    private lateinit var binding:ActivityProductListBinding
    private val networkMonitor = NetworkMonitorUtil(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        networkCheck(networkMonitor)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.cart,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.cart){
            val intent = Intent(this,CartActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun networkCheck(networkMonitor: NetworkMonitorUtil) {
        networkMonitor.result ={ isAvailable, type ->
            runOnUiThread {
                if (isAvailable) {
                   if (type == ConnectionType.Wifi ) {
                        Toast.makeText(this,"Connected : WIFI", Toast.LENGTH_SHORT).show()
                    } else if (type == ConnectionType.Cellular){
                       Toast.makeText(this,"Connected : Mobile", Toast.LENGTH_SHORT).show()
                   }
                }else {
                    Toast.makeText(this,"No Connection", Toast.LENGTH_SHORT).show()
                    println("Not connected")
                }

            }
        }

    }

    override fun onResume() {
        super.onResume()
        networkMonitor.register()
    }

    override fun onStop() {
        super.onStop()
        networkMonitor.unregister()
    }
}