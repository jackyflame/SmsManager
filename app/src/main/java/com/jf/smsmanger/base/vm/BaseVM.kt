package com.jf.smsmanger.base.vm

import android.databinding.BaseObservable
import com.jf.baselibraray.db.BasePresent

open class BaseVM<T : BasePresent> : BaseObservable {

    protected var mPresent:T? = null

    constructor(){}

    constructor(mPresent: T) :super(){
        this.mPresent = mPresent
    }
}