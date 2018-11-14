package com.jf.smsmanger.vm

import android.databinding.BindingAdapter
import android.widget.TextView

/**
 * Created by Android Studio.
 * ProjectName: ZxwlPro
 * Author: Haozi
 * Date: 2018/4/4
 * Time: 20:37
 */
class BindingAdapters {

    companion object {

        @BindingAdapter("style")
        @JvmStatic
        fun setTextViewStyle(view: TextView, style: Int) {
            if (style <= 0) {
                return
            }
           view.setTextAppearance(view.context,style)
        }

    }
}