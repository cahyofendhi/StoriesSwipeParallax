package com.storiesswipeparallax.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.storiesswipeparallax.R
import com.storiesswipeparallax.databinding.FragmentPageBinding
import kotlinx.android.synthetic.main.fragment_page.view.*

class ContentFragment: Fragment() {

    var binding: FragmentPageBinding? = null

    companion object {
        val EXTRA_DATA_CONTENT = "EXTRA_DATA_CONTENT"
        fun instance(data: String): Fragment{
            val bundle = Bundle()
            bundle.putString(EXTRA_DATA_CONTENT, data)
            val fragobj = ContentFragment()
            fragobj.arguments = bundle
            return fragobj
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(layoutInflater,
            R.layout.fragment_page, container, false)
        val data = arguments?.getString(EXTRA_DATA_CONTENT)
        data?.let { text ->
            binding?.title?.text = text
        }


        return binding?.root
    }

}