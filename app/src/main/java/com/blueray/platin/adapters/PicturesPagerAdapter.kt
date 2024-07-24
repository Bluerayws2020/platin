package com.blueray.platin.adapters

import androidx.viewpager.widget.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blueray.platin.R

class PicturesPagerAdapter () : PagerAdapter() {
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(container.context)
        val view = inflater.inflate(R.layout.item_pictur, container, false)
        // Bind picture data to the view
        //val picture = pictures[position]
        // ...
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int = 6

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view === `object`
}