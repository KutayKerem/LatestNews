package com.app.latestnews.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.latestnews.pages.BusinessFragment
import com.app.latestnews.pages.EntertainmentFragment
import com.app.latestnews.pages.GeneralFragment
import com.app.latestnews.pages.HealthFragment
import com.app.latestnews.pages.ScienceFragment
import com.app.latestnews.pages.TechnologyFragment

class ViewPagerAdapter(fragmentManager: FragmentManager,lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager,lifecycle) {



    override fun getItemCount(): Int {
       return 6
    }

    override fun createFragment(position: Int): Fragment {

        return when(position){
            0->{
                return GeneralFragment()
            }
            1->{
                return HealthFragment()
            }
            2->{
                return ScienceFragment()
            }
            3->{
                return EntertainmentFragment()
            }
            4->{
                return TechnologyFragment()
            }
            5->{
                return BusinessFragment()
            }
            else->{
                return GeneralFragment()

            }




        }

    }




}