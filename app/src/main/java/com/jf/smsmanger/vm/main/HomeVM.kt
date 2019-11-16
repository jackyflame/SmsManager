package com.jf.smsmanger.vm.main

import android.databinding.Bindable
import com.jf.smsmanger.R
import com.jf.smsmanger.base.vm.BaseVM
import com.jf.smsmanger.db.HomePresent
import com.jf.smsmanger.ui.main.HomeFragment
import kotlin.random.Random

class HomeVM(var framgent:HomeFragment) : BaseVM<HomePresent>(HomePresent()) {

    var imgArray = intArrayOf(R.mipmap.ic_home_funny_1,R.mipmap.ic_home_funny_2,R.mipmap.ic_home_funny_3,
        R.mipmap.ic_home_funny_4,R.mipmap.ic_home_funny_5,R.mipmap.ic_home_funny_6,R.mipmap.ic_home_funny_7)

    @Bindable
    var imgRes = R.mipmap.ic_home_funny_1
    get() {
        field = imgArray[Random.nextInt(imgArray.size)]
        return field
    }
}