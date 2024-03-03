import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yanuar.barita.databinding.ItemNewsBinding
import com.yanuar.barita.models.News

class NewsAdapter(private val listNews: ArrayList<News>) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    // Gunakan View Binding untuk ViewHolder
    inner class ViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)

    // Metode untuk membuat ViewHolder baru dengan View Binding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // Mengembalikan jumlah item dalam data set
    override fun getItemCount() = listNews.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            val news = listNews[position]
            textViewTitle.text = news.nameNews
            textViewDescription.text = news.nameNews
            // Atur data lainnya ke views dalam binding jika perlu
        }
    }
}
