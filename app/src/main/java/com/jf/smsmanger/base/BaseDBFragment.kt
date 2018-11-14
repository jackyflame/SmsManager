package com.haozi.zxwlpro.base

import android.app.ProgressDialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.jf.baselibraray.db.BasePresent
import com.jf.baselibraray.log.LogW
import com.jf.smsmanger.base.vm.BaseVM
import com.jf.smsmanger.BR

open class BaseDBFragment<T:ViewDataBinding,X: BaseVM<out BasePresent>> : Fragment() {

    lateinit var mRootView: View
    lateinit var mBinding: T
    var viewModel: X? = null
    lateinit var mInflater: LayoutInflater
    var mProgressDialog: ProgressDialog? = null

    fun bindLayout(inflater: LayoutInflater, layoutRes: Int, container: ViewGroup): T {
        return bindLayout(inflater, layoutRes, container, null)
    }

    fun bindLayout(inflater: LayoutInflater, layoutRes: Int, container: ViewGroup?, viewVm: X?): T {
        mInflater = inflater
        mBinding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        mRootView = mBinding.root
        viewModel = viewVm
        viewVm?.let{
            bindData(viewVm)
        }
        return mBinding
    }

    /**
     * @param t 绑定的数据模型 >
     * 绑定VM:XML布局内的VM必须命名为“viewModel”
     */
    fun bindData(t: Any) {
        bindData(BR.viewModel, t)
    }

    fun bindData(viewModelId: Int, vm: Any?) {
        if (mBinding != null && vm != null) {
            mBinding!!.setVariable(viewModelId, vm)
            mBinding!!.executePendingBindings()
        } else {
            LogW.i("bindData: failed cause bingd or vm is null")
        }
    }

    override fun onDestroy() {
        hideProgressDialog()
        super.onDestroy()
    }

    fun hideKeyboard() {
        try {
            val view = activity?.currentFocus
            if (view != null) {
                val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        } catch (e: Exception) {
            LogW.e("WTF!!!")
        }

    }

    fun showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog(activity)
        }
        if (!mProgressDialog!!.isShowing) {
            mProgressDialog!!.show()
        }
    }

    fun hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog!!.dismiss()
        }
    }
}