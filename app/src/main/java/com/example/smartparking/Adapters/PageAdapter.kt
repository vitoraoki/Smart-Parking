package com.example.smartparking.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.smartparking.Fragments.REventsListFragment
import com.example.smartparking.Fragments.UEventsListFragment

class PageAdapter(fragment: FragmentManager): FragmentPagerAdapter(fragment, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    lateinit var uEventsListFragment: UEventsListFragment
    lateinit var rEventsListFragment: REventsListFragment

    override fun getItem(position: Int): Fragment {
        if (position == 0) {
            if (!this::rEventsListFragment.isInitialized) {
                rEventsListFragment = REventsListFragment()
            }
            return rEventsListFragment
        } else {
            if (!this::uEventsListFragment.isInitialized) {
                uEventsListFragment = UEventsListFragment()
            }
            return uEventsListFragment
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        if (position == 0) {
            return "Eventos Repetitivos"
        } else {
            return "Eventos Únicos"
        }
    }
}