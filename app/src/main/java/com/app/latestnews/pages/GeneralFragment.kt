package com.app.latestnews.pages

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.latestnews.adapter.NewsAdapter
import com.app.latestnews.databinding.FragmentGeneralBinding
import com.app.latestnews.model.NewsModel
import com.app.latestnews.model.mainModel
import com.app.latestnews.service.initAPİ
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class GeneralFragment : Fragment() {

    lateinit var binding: FragmentGeneralBinding
    private val BASE_APİ = "11a856d5fd7748cd85d6c107277ea8d3"
    private val BASE_APİ2 = "04e94d0b048242308d6cf8f9eb5af9e0"
    var compositeDisposable: CompositeDisposable? = null
    lateinit var newsModelArrayList : ArrayList<NewsModel>
    lateinit var adapter  : NewsAdapter
    var sharedPreferences : SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGeneralBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        newsModelArrayList = ArrayList()

        var layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager



        getNews()




    }

    fun getNews(){

        var retrofit =  initAPİ.getRetrofit()
        val disposable = retrofit.getNews("us", 100, BASE_APİ2)
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

        val editor = requireActivity().getSharedPreferences("News", MODE_PRIVATE).edit()

        val jsonStringNewsList = GsonBuilder().create().toJson(mainModel.articles)
        editor.remove("General")
        editor.putString("General",jsonStringNewsList)
        editor.apply()

    }

    fun getJsonSharedPrefences(){
        val editor = requireActivity().getSharedPreferences("News", MODE_PRIVATE)


        val jsonString = editor.getString("General", null)
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




}