package com.rohanrana.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.rohanrana.memeshare.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var currentMemeUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadMeme()
        setupButtons()
    }

    private fun loadMeme() {

        binding.progressBar.visibility = View.VISIBLE

        val url = " https://meme-api.herokuapp.com/gimme"
        currentMemeUrl = url

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->

                val url = response.getString("url")
                Glide.with(this).load(url).listener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressBar.visibility = View.GONE
                        return false
                    }
                }).into(binding.imageViewMeme)

            },
            { error ->
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        )
        MySingleton.MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }
    private fun setupButtons() {

        binding.buttonNext.setOnClickListener {
            loadMeme()
        }
        binding.buttonShare.setOnClickListener {

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT,"Check out this Meme : $currentMemeUrl")
            val chooser = Intent.createChooser(intent,"Share using : ")
            startActivity(chooser)
        }
    }

}