import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.yanuar.barita.R
import com.yanuar.barita.databinding.ItemNewsBinding
import com.yanuar.barita.models.News
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class NewsAdapter(private var newsList: List<News>, private val onItemClick: (String) -> Unit) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun formatDateString(originalDateString: String): String {
            // Parse the original date string
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            parser.timeZone = TimeZone.getTimeZone("UTC") // Ensure the parser is set to UTC
            val date = parser.parse(originalDateString)

            // Format the date
            val formatter = SimpleDateFormat("d MMMM yyyy", Locale("id", "ID")) // Indonesian locale for "Maret" etc.
            return formatter.format(date)
        }

        fun bind(item: News, isSelected: Boolean){
            val textView = itemView.findViewById<TextView>(R.id.id_tv_judul_berita)
            val textViewDescription = itemView.findViewById<TextView>(R.id.id_content_berita)
            val textViewDatePublish = itemView.findViewById<TextView>(R.id.id_tanggal_berita)
            val imageView = itemView.findViewById<ImageView>(R.id.id_img_berita)

            Glide.with(itemView.context)
                .load(item.thumbnail)
                .into(imageView)
            textView.text = item.title
            textViewDescription.text = item.description
            textViewDatePublish.text = formatDateString(item.pubDate)

            textView.setOnClickListener {
                onItemClick(item.title)
                if (selectedPosition != adapterPosition) {
                    notifyItemChanged(selectedPosition)
                    selectedPosition = adapterPosition
                    notifyItemChanged(selectedPosition)
                }
            }
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

