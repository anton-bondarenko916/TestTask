package com.example.testtask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.SimpleAdapter
import com.example.testtask.databinding.SimpleListViewBinding
import com.google.gson.Gson

import java.net.URL

import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {

    private lateinit var binding : SimpleListViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SimpleListViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpListView()
    }

    private fun setUpListView() {

        val dp = DataPage()
        dp.request()

        val data = mutableListOf<Map<String, String>>()
        dp.data?.forEach {
            data.add(mapOf(
                KEY_TITLE to it.Name,
                KEY_DESCRIPTION to it.Value.toString() + " руб."))
        }

        val adapter = SimpleAdapter(
            this,
            data,
            android.R.layout.simple_list_item_2,
            arrayOf(KEY_TITLE, KEY_DESCRIPTION),
            intArrayOf(android.R.id.text1, android.R.id.text2)
        )
        binding.listView.adapter = adapter
    }

    companion object {
        @JvmStatic val KEY_TITLE = "title"
        @JvmStatic val KEY_DESCRIPTION = "description"
    }
}