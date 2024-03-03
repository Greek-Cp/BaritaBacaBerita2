package com.yanuar.barita.ui.detail

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.yanuar.barita.R
import com.yanuar.barita.databinding.ActivityDetailBinding
import com.yanuar.barita.databinding.ActivityMainBinding
import com.yanuar.barita.helper.UserPreferences
import com.yanuar.barita.models.News

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val news = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("EXTRA_NEWS", News::class.java)
        } else {
            intent.getParcelableExtra<News>("EXTRA_NEWS")
        }
        val userPreferences = UserPreferences(applicationContext)
        binding.buttonBack.setOnClickListener({
            finish()
        })
        binding.idBtnSimpanCerita.setOnClickListener({

            userPreferences.addBeritaDisimpan(news!!,applicationContext)
        })
        binding.idTvDetailJudulBerita.text = news?.title
        val shimmer = Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
            .setDuration(2800) // how long the shimmering animation takes to do one full sweep
            .setBaseAlpha(0.7f) //the alpha of the underlying children
            .setHighlightAlpha(0.6f) // the shimmer alpha amount
            .setDirection(Shimmer.Direction.RIGHT_TO_LEFT)
            .setAutoStart(true)
            .build()
        val shimmerDrawable = ShimmerDrawable().apply {
            setShimmer(shimmer)
        }
        Glide.with(this)
            .load(news?.topImage)
            .placeholder(shimmerDrawable).error(shimmerDrawable)
            .into(binding.idImgBerita)
        binding.idTvDetailContenBeritaHeader.text = news?.shortDescription
        binding.idTvDetailContenBerita.text = news?.text
        binding.actionShare.setOnClickListener({
            userPreferences.incrementBeritaDibagikan()
            Toast.makeText(applicationContext, "Membagikan Berita Anda", Toast.LENGTH_SHORT).show()
            shareText(applicationContext,"Saya Membagikan Berita ${news?.title}\n ${news?.text?.take(50) ?: ""
            }...\nUntuk Detail Berita Bisa Kunjungi ${news?.url}")
        })
    }
    fun shareText(context: Context, text: String, title: String = "Share via") {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, title)
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)
        context.startActivity(Intent.createChooser(shareIntent, title).apply {
            addFlags(FLAG_ACTIVITY_NEW_TASK)
        })
    }
}