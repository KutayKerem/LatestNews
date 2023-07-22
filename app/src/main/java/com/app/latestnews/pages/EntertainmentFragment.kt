package com.app.latestnews.pages

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.latestnews.adapter.NewsAdapter
import com.app.latestnews.databinding.FragmentEntertainmentBinding
import com.app.latestnews.model.NewsModel
import com.app.latestnews.model.mainModel
import com.app.latestnews.service.initAPİ
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class EntertainmentFragment : Fragment() {

    lateinit var binding:  FragmentEntertainmentBinding
    private val BASE_APİ = "11a856d5fd7748cd85d6c107277ea8d3"
    private val BASE_APİ2 = "04e94d0b048242308d6cf8f9eb5af9e0"
    var compositeDisposable: CompositeDisposable? = null
    lateinit var newsModelArrayList : ArrayList<NewsModel>
    lateinit var adapter  : NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEntertainmentBinding.inflate(layoutInflater,container,false)
        return  binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsModelArrayList = ArrayList()

        var layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager

        getNews()
    }


    fun getNews(){
        newsModelArrayList.clear()

        var retrofit =  initAPİ.getRetrofit()
        val disposable = retrofit.getCategoryNews("us","entertainment", 100, BASE_APİ2)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { mainModel ->
                    // API yanıtı başarılı olduğunda yapılacak işlemler
                    handleResponse(mainModel)
                },
                { throwable ->
                    // Hata durumunda yapılacak işlemler
                    println("Hata oluştu: ${throwable.message}")
                    getJsonSharedPrefences()
                }
            )
        compositeDisposable?.add(disposable)




    }
    fun handleResponse(mainModel: mainModel){
        newsModelArrayList.addAll(mainModel.articles)
        adapter = NewsAdapter(requireContext(), newsModelArrayList)
        binding.recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

        val editor = requireActivity().getSharedPreferences("News", Context.MODE_PRIVATE).edit()

        val jsonStringNewsList = GsonBuilder().create().toJson(mainModel.articles)
        editor.remove("Entertainment")
        editor.putString("Entertainment",jsonStringNewsList)
        editor.apply()

    }

    fun getJsonSharedPrefences(){
        val editor = requireActivity().getSharedPreferences("News", Context.MODE_PRIVATE)


        val jsonString = editor.getString("Entertainment", null)
        val typeToken = object : TypeToken<ArrayList<NewsModel>>() {}.type


        if (jsonString != null) {
            val data: ArrayList<NewsModel> = GsonBuilder().create().fromJson(jsonString, typeToken)
            newsModelArrayList.addAll(data)
            adapter = NewsAdapter(requireContext(), newsModelArrayList)
            binding.recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }

    override fun onResume() {
        super.onResume()



    }
}


