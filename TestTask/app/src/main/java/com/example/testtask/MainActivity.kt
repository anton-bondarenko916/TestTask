package com.example.testtask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SimpleAdapter
import com.example.testtask.databinding.SimpleListViewBinding
import org.json.JSONObject
import java.lang.Exception
import java.net.URL
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {

    private lateinit var binding : SimpleListViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SimpleListViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpListView()
    }


    fun setUpListView() {
        val dp = DataPage()
        dp.request()
        Thread.sleep(1000)

        val list = dp.data
        print(list?.size)

        val data = mutableListOf<Map<String, String>>()
        list?.forEach {
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