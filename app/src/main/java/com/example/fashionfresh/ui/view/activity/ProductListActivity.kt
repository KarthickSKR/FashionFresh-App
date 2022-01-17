package com.example.fashionfresh.ui.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
        networkCheck(networkMonitor, binding)

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

    private fun networkCheck(
        networkMonitor: NetworkMonitorUtil,
        binding: ActivityProductListBinding
    ) {
        networkMonitor.result ={ isAvailable, type ->
            runOnUiThread {
                when {
                    isAvailable -> {
                        when (type) {
                            ConnectionType.Wifi -> {
                                binding.tvNetworkCheck.text = getString(R.string.wifi_connected)
                            }
                            ConnectionType.Cellular -> {
                                binding.tvNetworkCheck.text = getString(R.string.cellular_connected)
                            }
                        }
                        binding.tvNetworkCheck.visibility = View.VISIBLE
                        binding.tvNetworkCheck.setBackgroundColor(ContextCompat.getColor(this,R.color.Green))
                        handleNetworkStatusVisibility(binding)
                    }
                    else -> {
                        binding.tvNetworkCheck.visibility = View.VISIBLE
                        binding.tvNetworkCheck.text = getString(R.string.no_connection)
                        binding.tvNetworkCheck.setBackgroundColor(ContextCompat.getColor(this,R.color.LightRed))
          //              Toast.makeText(this,"No Connection", Toast.LENGTH_SHORT).show()
                        println("Not connected")
                        handleNetworkStatusVisibility(binding)
                    }
                }

            }
        }

    }

    private fun handleNetworkStatusVisibility(binding: ActivityProductListBinding) {
        binding.tvNetworkCheck.postDelayed({
                binding.tvNetworkCheck.visibility = View.GONE
        },3000)
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