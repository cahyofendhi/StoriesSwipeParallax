package com.storiesswipeparallax

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.util.Log
import com.storiesswipeparallax.databinding.ActivityMainBinding
import com.storiesswipeparallax.widget.ViewPageScroller
import com.storiesswipeparallax.widget.setViewPageScroller
import jp.shts.android.storiesprogressview.StoriesProgressView
import java.util.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity(), StoriesProgressView.StoriesListener {

    private val LOG_DEBUG = "PARALAX"

    private var imageModelArrayList: ArrayList<ImageModel>? = null

    private val myImageList = intArrayOf(R.drawable.sample1,
                                            R.drawable.sample2,
                                            R.drawable.sample3,
                                            R.drawable.sample4,
                                            R.drawable.sample5,
                                            R.drawable.sample6)

    lateinit var binding: ActivityMainBinding
    lateinit var mPager: ViewPager
    lateinit var pagerAdapter : ViewPagerAdapter
    lateinit var storiesProgressView: StoriesProgressView
    private var counter = 0
    private var currentPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        imageModelArrayList = ArrayList()
        imageModelArrayList = populateList()

        init()

    }

    private fun populateList(): ArrayList<ImageModel> {

        val list = ArrayList<ImageModel>()

        for (i in 0..5) {
            val imageModel = ImageModel()
            imageModel.setImage_drawables(myImageList[i])
            list.add(imageModel)
        }

        return list
    }

    private fun init() {
        storiesProgressView = binding.stories
        storiesProgressView.setStoriesCount(myImageList.size)
        storiesProgressView.setStoryDuration(10000L)
        storiesProgressView.setStoriesListener(this)

        mPager = binding.vp
        mPager.adapter = SlidingImageAdapter(this@MainActivity, this.imageModelArrayList!!)


        mPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {
                Log.d(LOG_DEBUG, "Page Index : $counter")
            }
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) { }
            override fun onPageSelected(p0: Int) {}
        })
        mPager.setViewPageScroller(ViewPageScroller(this))

        storiesProgressView.startStories()

        pagerAdapter = ViewPagerAdapter(manager = supportFragmentManager)
        myImageList.forEach {
            pagerAdapter.addFragment(ContentFragment(), null)
        }
        binding.container.adapter = pagerAdapter
        var isHalf = false
        binding.container.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {
                currentPage = p0
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
                val percent = (p1/1 * 100).roundToInt()

                if (percent == 50){
                    mPager.setCurrentItem(currentPage + 1, true)
                } else if (percent == 49) {
                    mPager.setCurrentItem(currentPage - 1, true)
                }
            }

            override fun onPageSelected(p0: Int) {
                binding.container.currentItem = p0
                if (p0 < counter){
                    storiesProgressView.reverse()
                } else if (p0 > counter){
                    storiesProgressView.skip()
                }
            }

        })
    }

    override fun onComplete() {
//        storiesProgressView.destroy()
    }

    override fun onPrev() {
        val prevIndex = --counter
        Log.d(LOG_DEBUG, "Prev Index : $prevIndex")
        mPager.setCurrentItem(prevIndex, true)
    }

    override fun onNext() {
        val nextIndex = ++counter
        Log.d(LOG_DEBUG, "Next Index : $nextIndex")
        mPager.setCurrentItem(nextIndex, true)
    }
}
