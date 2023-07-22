package com.app.latestnews.pages

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.addCallback
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.app.latestnews.databinding.FragmentWebNewBinding


class WebNewFragment : Fragment() {

    lateinit var binding:FragmentWebNewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWebNewBinding.inflate(layoutInflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {


        if(arguments != null){
            var url = WebNewFragmentArgs.fromBundle(requireArguments()).url
            binding.webView.loadUrl(url)
            binding.webView.settings.javaScriptEnabled = true


            binding.webView.webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    binding.progressBar.visibility = View.VISIBLE
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    binding.progressBar.visibility = View.GONE
                }
            }







        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            var action : NavDirections = WebNewFragmentDirections.actionWebNewFragmentToHomeFragment()
            Navigation.findNavController(view).navigate(action)

        }
        binding.backWeb.setOnClickListener {
            var action : NavDirections = WebNewFragmentDirections.actionWebNewFragmentToHomeFragment()
            Navigation.findNavController(view).navigate(action)
        }
    }catch (e:Exception){
        Log.d("Error:",e.localizedMessage)
    }




    }



}