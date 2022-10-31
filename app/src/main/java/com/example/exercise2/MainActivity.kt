package com.example.exercise2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.exercise2.model.remote.ItunesNetwork
import com.example.exercise2.model.remote.MusicResponse
import com.example.exercise2.view.adapter.TypeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var bar: BottomNavigationView
    private lateinit var srl: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bar = findViewById(R.id.bar)

        bar.selectedItemId = R.id.classic
        retrofitCall("classick")

        bar.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.rock -> retrofitCall("rock")
                R.id.classic -> retrofitCall("classick")
                R.id.pop -> retrofitCall("pop")
            }
            true
        }

        srl = findViewById(R.id.srl)
        srl.setOnRefreshListener {
            when(bar.selectedItemId){
                R.id.rock -> retrofitCall("rock")
                R.id.classic -> retrofitCall("classick")
                R.id.pop -> retrofitCall("pop")
            }
            Handler().postDelayed(Runnable {
                srl.isRefreshing = false
            }, 1000)
        }
    }

    private fun retrofitCall(type: String){
        ItunesNetwork.itunesApi.getSongs(type).enqueue(
            object : Callback<MusicResponse> {
                override fun onResponse(
                    call: Call<MusicResponse>,
                    response: Response<MusicResponse>
                ) {
                    if(response.isSuccessful){
                        val body = response.body()
                        loadFragment(body)
                        Log.d("TAG", "onResponse: success $body ${response.code()} ${call.request().toString()}")
                    }
                }

                override fun onFailure(call: Call<MusicResponse>, t: Throwable) {
                    Log.d("TAG", "fail ${t.cause}")
                }
            }
        )
    }

    private fun loadFragment(body: MusicResponse?) {
        body?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, TypeFragment.newInstance(it))
                .commit()
        }
    }
}