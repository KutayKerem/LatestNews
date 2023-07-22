package com.app.latestnews.pages

//noinspection SuspiciousImport,SuspiciousImport

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.app.latestnews.R
import com.app.latestnews.adapter.ViewPagerAdapter
import com.app.latestnews.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : Fragment() {


    lateinit var binding : FragmentHomeBinding

    lateinit var viewPagerAdapter : ViewPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        return  binding.root
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





        try {


            val white = ContextCompat.getColor(requireContext(), com.app.latestnews.R.color.white)
            val dark = ContextCompat.getColor(requireContext(), com.app.latestnews.R.color.dark)
            val selected = ContextCompat.getColor(requireContext(), com.app.latestnews.R.color.bordo)



            when (this.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    binding.tabLayout.setTabTextColors(white,selected)

                }
                Configuration.UI_MODE_NIGHT_NO -> {
                    binding.tabLayout.setTabTextColors(dark,selected)
                }
                Configuration.UI_MODE_NIGHT_UNDEFINED -> {

                }
            }




            if (requireActivity().supportFragmentManager != null){
                viewPagerAdapter = ViewPagerAdapter(requireActivity().supportFragmentManager,lifecycle)
                binding.viewPager.adapter = viewPagerAdapter

                TabLayoutMediator ( binding.tabLayout,binding.viewPager){ tab,position ->
                    when(position){
                        0->{
                            tab.text = "Home"
                        }
                        1->{
                            tab.text = "Health"
                        }
                        2->{
                            tab.text = "Science"
                        }
                        3->{
                            tab.text = "Entertainment"
                        }
                        4->{
                            tab.text = "Technology"
                        }
                        5->{
                            tab.text = "Business"
                        }


                    }

                }.attach()
            }




        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            ExitDialog(view)
        }


        }catch (e:Exception){
            Log.d("Error:",e.localizedMessage)
        }


    }

    @SuppressLint("ResourceAsColor")
    fun ExitDialog(view:View){

        Snackbar.make(view,"Press once more to exit", Snackbar.LENGTH_SHORT).setActionTextColor(
            com.app.latestnews.R.color.bordo
        ).setAction("Exit") { view ->
            val setIntent = Intent(Intent.ACTION_MAIN)
            setIntent.addCategory(Intent.CATEGORY_HOME)
            setIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(setIntent)
        }.show()


    }


}