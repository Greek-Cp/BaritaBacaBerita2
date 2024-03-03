import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.google.android.material.card.MaterialCardView
import com.yanuar.barita.R
import com.yanuar.barita.databinding.ItemNewsBinding
import com.yanuar.barita.models.News
import com.yanuar.barita.ui.detail.DetailActivity
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

class NewsAdapter(private var newsList: List<News>, private val onItemClick: (String) -> Unit,private var listener: OnImageLoadErrorListener) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    interface OnImageLoadErrorListener {
        fun onImageLoadError(position: Int)
    }
    fun setImageLoadErrorListener(listener: OnImageLoadErrorListener) {
        this.listener = listener
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun formatDateString(originalDateString: String): String {
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            parser.timeZone = TimeZone.getTimeZone("UTC") // Ensure the parser is set to UTC
            val date = parser.parse(originalDateString)
            val formatter = SimpleDateFormat("d MMMM yyyy", Locale("id", "ID")) // Indonesian locale for "Maret" etc.
            return formatter.format(date)
        }

        fun bind(item: News, isSelected: Boolean){
            val textView = itemView.findViewById<TextView>(R.id.id_tv_judul_berita)
            val textViewDescription = itemView.findViewById<TextView>(R.id.id_content_berita)
            val textViewDatePublish = itemView.findViewById<TextView>(R.id.id_tanggal_berita)
            val imageView = itemView.findViewById<ImageView>(R.id.id_img_berita)
             val shimmer = Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
                .setDuration(1800) // how long the shimmering animation takes to do one full sweep
                .setBaseAlpha(0.7f) //the alpha of the underlying children
                .setHighlightAlpha(0.6f) // the shimmer alpha amount
                .setDirection(Shimmer.Direction.RIGHT_TO_LEFT)
                .setAutoStart(true)
                .build()

            val shimmerDrawable = ShimmerDrawable().apply {
                setShimmer(shimmer)
            }

            Glide.with(itemView.context)
                .load(item.topImage)
                .placeholder(shimmerDrawable)
                .listener(object : RequestListener<Drawable> {


                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        val position = adapterPosition
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onImageLoadError(position)
                        }
                        return true
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                })
                .into(imageView)
            textView.text = item.title
            textViewDescription.text = truncateText(item.shortDescription,88)
            textViewDatePublish.text = item.date

            itemView.setOnClickListener {
                onItemClick(item.title)
                val intent = Intent(itemView.context, DetailActivity::class.java).apply {
                    putExtra("EXTRA_NEWS", item)
                }
                itemView.context.startActivity(intent)

                if (selectedPosition != adapterPosition) {
                    notifyItemChanged(selectedPosition)
                    selectedPosition = adapterPosition
                    notifyItemChanged(selectedPosition)
                }
            }
        }
    }
    fun truncateText(text: String, maxLength: Int): String {
        return if (text.length > maxLength) {
            text.substring(0, maxLength - 3) + "..."
        } else {
            text
        }
    }
    var selectedPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = newsList[position]
        val isSelected = position == selectedPosition
        holder.bind(category, isSelected)
    }

    override fun getItemCount(): Int = newsList.size
    fun updateNews(newsList: List<News>) {
        this.newsList = newsList
        notifyDataSetChanged()
    }
}

