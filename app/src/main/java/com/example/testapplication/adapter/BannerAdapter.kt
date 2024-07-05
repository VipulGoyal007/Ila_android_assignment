package com.example.testapplication.adapter

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.testapplication.R
import com.example.testapplication.databinding.BannerItemBinding
import com.example.testapplication.datamodel.BannerDataModel


class BannerAdapter(
    context: Context,
    private val bannerItems: List<BannerDataModel>,
    private val viewPager: ViewPager,
    private val swipeDelay: Long
) : PagerAdapter() {
    private val layoutInflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    private val handler: Handler = Handler(Looper.getMainLooper())
    private var autoSwipeRunnable: Runnable? = null
    private var currentPosition = 0

    override fun getCount(): Int {
        return bannerItems.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    init {
        if (swipeDelay > 0 && bannerItems.size > 1)
            startAutoSwipe()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding: BannerItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.banner_item, container, false)
        val bannerItem = bannerItems[position]

        if (bannerItem.imageUrl != null)
            binding.image.setImageDrawable(bannerItem.imageUrl)

        binding.cvBanner.setOnClickListener {

        }

        //binding.bannerItem = bannerItem
        container.addView(binding.root)


        return binding.root
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        // stopAutoSwipe()
        container.removeView(`object` as View)
    }

    //note :- swipe functionality
    private fun startAutoSwipe() {
        if (autoSwipeRunnable == null) {
            autoSwipeRunnable = object : Runnable {
                override fun run() {
                    if (currentPosition == count - 1) {
                        currentPosition = 0
                    } else {
                        currentPosition++
                    }
                    viewPager.setCurrentItem(currentPosition, true)

                    handler.postDelayed(this, swipeDelay)
                }
            }
            autoSwipeRunnable?.let {
                handler.postDelayed(it, swipeDelay)
            }
        }
    }


    private fun stopAutoSwipe() {
        autoSwipeRunnable?.let {
            handler.removeCallbacks(it)
            autoSwipeRunnable = null
        }
    }

    fun updateCurrentPosition(newPosition: Int) {
        currentPosition = newPosition
    }


}