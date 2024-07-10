package com.example.testapplication

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.example.testapplication.adapter.BannerAdapter
import com.example.testapplication.bottomsheet.SearchDataBottomSheet
import com.example.testapplication.databinding.ActivityMainBinding
import com.example.testapplication.datamodel.MESSAGES
import com.example.testapplication.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var swipeDelay: Int = 0
    private lateinit var bannerAdapter: BannerAdapter
    private var pageCount = 0
    private var activePage = 0
    lateinit var slider :LinearLayout
    private lateinit var searchDataBottomSheet: SearchDataBottomSheet
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        slider = binding.llBannerIndicator

        // setting observer data
        setObservers()

        //search functionality implemented here
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {

                viewModel.performFilterOperation(s.toString().trim())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })



       viewModel.flowUsage()

    }

    fun setObservers() {
        viewModel.setBannerListLiveData.observe(this) {
            if (!it.isNullOrEmpty() && it.size > 0) {



                //setting banner adapter data
                bannerAdapter = BannerAdapter(
                    this, it, binding.vpBanner, swipeDelay * 1000.toLong()
                )

                with(binding.vpBanner) {
                    adapter = bannerAdapter

                    setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                        override fun onPageScrolled(
                            position: Int,
                            positionOffset: Float,
                            positionOffsetPixels: Int
                        ) {

                            bannerAdapter.updateCurrentPosition(position)

                        }

                        override fun onPageSelected(position: Int) {
                            setActivePage(position
                            )
                            binding.etSearch.setText("")
                            viewModel.getSearchListing(position)
                        }

                        override fun onPageScrollStateChanged(state: Int) {}
                    })


                }

                //setting pager indicator
                setActivePage(0)
                setPageCount(it.size)
                setViewPageIndicator()
            }
        }

        viewModel.showBottomSheetDialogLiveData.observe(this) {
            if(it.equals(MESSAGES.BOTTOM_SHEET_DATA_FOUND)){
                searchDataBottomSheet =
                    SearchDataBottomSheet(viewModel.bottomSheetTitlelist, viewModel.countData)
                searchDataBottomSheet.isCancelable = true
                if (!searchDataBottomSheet.isVisible) {
                    searchDataBottomSheet.show(
                       this.supportFragmentManager,
                        "SearchListBottomsheet"
                    )

                }
            }
           else if(it.equals(MESSAGES.BOTTOM_SHEET_NO_DATA_FOUND))
           {
               Toast.makeText(this,"No List data found",Toast.LENGTH_LONG).show()
           }
        }

    }
    fun setPageCount(pageCount: Int) {
        this.pageCount = pageCount
        binding.llBannerIndicator.requestLayout()
        binding.llBannerIndicator.invalidate()
    }
    fun setActivePage(activePage: Int) {
        this.activePage = activePage
        binding.llBannerIndicator.invalidate()
        setViewPageIndicator()
    }

    fun setViewPageIndicator() {

        if (slider != null) {
            slider.removeAllViews()
            for (i in 0 until pageCount) {
                val view = AppCompatImageView(this)
                if (activePage == i) {
                    view.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_active))

                } else {
                    view.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_inactive))

                }
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                val marginLeft: Int = dpToPixel(view.context, 4f)
                params.setMargins(marginLeft, 0, 0, 0)
                view.layoutParams = params
                view.layoutParams .width=24
                view.layoutParams .height=24
                slider.addView(view)
            }
        }
    }

    private fun dpToPixel(context: Context?, dp: Float): Int {
        return if (context != null) {
            val density = context.resources.displayMetrics.density
            val pixel = dp * density
            pixel.toInt()
        } else {
            dp.toInt()
        }
    }
}

