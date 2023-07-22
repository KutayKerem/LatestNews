package com.app.latestnews.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.app.latestnews.databinding.RecyclerRowNewsBinding
import com.app.latestnews.model.NewsModel
import com.app.latestnews.pages.HomeFragmentDirections
import java.text.SimpleDateFormat
import java.util.Locale

class NewsAdapter(val context : Context, var newsModelArrayList: ArrayList<NewsModel>) : RecyclerView.Adapter<NewsAdapter.NewsHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        var binding = RecyclerRowNewsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NewsHolder(binding)
    }


    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        holder.heading.text = newsModelArrayList.get(position).title
        holder.aciklama.text = newsModelArrayList.get(position).description
        holder.author.text = newsModelArrayList.get(position).author
        holder.time.text = newsModelArrayList.get(position).publishedAt

        Glide.with(context).load(newsModelArrayList.get(position).urlToImage) .override(250,250).into(holder.image)








        val date = convertDateTimeToDateString(newsModelArrayList.get(position).publishedAt)
        holder.time.text = date


        holder.linear.setOnClickListener {
           var action : NavDirections = HomeFragmentDirections.actionHomeFragmentToWebNewFragment(newsModelArrayList.get(position).url)
            Navigation.findNavController(it).navigate(action)

        }




    }
    override fun getItemCount(): Int {
        return newsModelArrayList.size
    }


    class  NewsHolder(binding: RecyclerRowNewsBinding) : RecyclerView.ViewHolder(binding.root){
        var heading = binding.newBaslik
        var aciklama = binding.newAciklama
        var time = binding.newTime
        var image = binding.newImage
        var author = binding.newAuthor
        var linear = binding.newsLinear



    }

    fun convertDateTimeToDateString(dateTime: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        try {
            val date = inputFormat.parse(dateTime)
            return outputFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }



}