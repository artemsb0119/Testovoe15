package com.example.testovoe15

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Math.abs
import java.net.URL

class RecommendationsActivity : AppCompatActivity() {

    private lateinit var advices: List<Advice>
    private lateinit var activity: Activity
    private lateinit var imageViewFon2: ImageView

    private lateinit var viewpager: ViewPager2
    private lateinit var adapter: RecommendationsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommendations)

        viewpager = findViewById(R.id.viewpager)
        imageViewFon2 = findViewById(R.id.imageViewFon2)
        activity = this

        Glide.with(this)
            .load("http://135.181.248.237/15/fon2.png")
            .into(imageViewFon2)

        loadData()
    }

    private fun loadData() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val data = URL("http://135.181.248.237/15/advices.json").readText()
                val gson = Gson()
                advices = gson.fromJson(data, Array<Advice>::class.java).toList()

                activity.runOnUiThread {
                    adapter = RecommendationsAdapter(advices)
                    adapter = RecommendationsAdapter(advices)
                    viewpager.adapter = adapter

                    viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                        override fun onPageSelected(position: Int) {
                            super.onPageSelected(position)
                            if (position == 0) {
                                viewpager.setCurrentItem(adapter.itemCount - 2, false)
                            } else if (position == adapter.itemCount - 1) {
                                viewpager.setCurrentItem(0, false)
                            }
                        }
                    })

                    val compositePageTransformer = CompositePageTransformer()
                    compositePageTransformer.addTransformer { page, position ->
                        val absPosition = abs(position)
                        page.scaleY = 1f - absPosition * 0.15f
                    }
                    viewpager.setPageTransformer(compositePageTransformer)

                    viewpager.setCurrentItem(0, false)

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}