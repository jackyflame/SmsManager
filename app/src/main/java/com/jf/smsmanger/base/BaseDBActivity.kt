package com.haozi.zxwlpro.base

import android.app.ProgressDialog
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.jf.baselibraray.db.BasePresent
import com.jf.baselibraray.interfaces.views.IActivityViewTools
import com.jf.baselibraray.log.LogW
import com.jf.smsmanger.BR
import com.jf.smsmanger.R
import com.jf.smsmanger.base.vm.BaseVM
import com.vondear.rxtool.view.RxToast

open class BaseDBActivity<X:ViewDataBinding, T: BaseVM<out BasePresent>> : AppCompatActivity(), IActivityViewTools {

    var progressDialog:ProgressDialog? = null
    var progressContent: TextView? = null
    var viewModel: T? = null
    lateinit var mBinding: X
    var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun bindLayout(layoutRes: Int): X {
        return bindLayout(layoutRes, null, true)
    }

    fun bindLayout(layoutRes: Int, vm: T): X {
        return bindLayout(layoutRes, vm, true)
    }

    fun bindLayout(layoutRes: Int, viewModel: T?, homeAsUp: Boolean): X {
        return bindLayout(layoutRes,viewModel,homeAsUp,-1)
    }

    fun bindLayout(layoutRes: Int, viewModel: T?, homeAsUp: Boolean,titleRes: Int): X {
        //绑定页面
        mBinding = DataBindingUtil.setContentView(this, layoutRes)
        //操作titlebar
        initTitleBar(homeAsUp,titleRes)
        //绑定viewModel
        this.viewModel = viewModel
        viewModel?.let {
            bindData(viewModel)
        }
        //返回mbinding
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            mBinding.executePendingBindings()
        }
        return mBinding
    }

    private fun initTitleBar(homeAsUp: Boolean,titleRes: Int) {
        toolbar = findViewById(R.id.toolbar)
        val titlebar = findViewById<TextView>(R.id.toolbar_title)
        if (toolbar != null) {
            toolbar?.subtitle = ""
            setSupportActionBar(toolbar)
            if (supportActionBar != null) {
                supportActionBar?.setDisplayHomeAsUpEnabled(homeAsUp)
                //if (homeAsUp == false) {
                //    toolbar.setNavigationIcon(R.drawable.ic_arrow_back_selector);
                //}
            }
            if (titlebar != null) {
                //设置标题
                if(titleRes > 0){
                    titlebar.setText(titleRes)
                    titlebar.setBackgroundColor(resources.getColor(R.color.transparent))
                }
                toolbar?.title = ""
                supportActionBar?.setDisplayShowTitleEnabled(false)
            }
        }
    }

    /**
     * @param t 绑定的数据模型
     * 绑定VM:XML布局内的VM必须命名为“viewModel”
     */
    fun bindData(t: T) {
        bindData(BR.viewModel, t)
    }

    fun bindData(viewModelId: Int, vm: T) {
        if (mBinding != null) {
            mBinding?.setVariable(viewModelId, vm)
            mBinding?.executePendingBindings()
        } else {
            LogW.i("bindData: failed cause bingd or vm is null")
        }
    }

    override fun setTitle(textRes: Int) {
        val toolbar:Toolbar? = findViewById(R.id.toolbar)
        val titleBar:TextView? = findViewById(R.id.toolbar_title)
        if (titleBar != null) {
            titleBar.setText(textRes)
            titleBar.setBackgroundColor(resources.getColor(R.color.transparent))
        } else if (toolbar != null) {
            toolbar.setTitle(textRes)
        }
    }

    open fun setTitle(text: String) {
        val toolbar:Toolbar? = findViewById(R.id.toolbar)
        val titleBar:TextView? = findViewById(R.id.toolbar_title)
        if (titleBar != null) {
            titleBar.text = text
            titleBar.setBackgroundColor(resources.getColor(R.color.transparent))
        } else if (toolbar != null) {
            toolbar.title = text
        }
    }

    fun setTitleImg(imgRes:Int){
        if(imgRes > 0){
            val toolbar:Toolbar = findViewById(R.id.toolbar)
            val titleBar:TextView = findViewById(R.id.toolbar_title)
            titleBar.text = ""
            toolbar.title = ""
            titleBar.setBackgroundResource(imgRes)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        hideProgressDialog()
        super.onDestroy()
    }

    override fun showProgressDialog() {
        showProgressDialog(true)
    }

    override fun showProgressDialog(cancelable: Boolean) {
        showProgressDialog(cancelable, null)
    }

    override fun showProgressDialog(cancelable: Boolean, content: String?) {
        if (isFinishing) return
        if (progressDialog == null) {
            progressDialog = ProgressDialog(this)
            progressDialog?.setCancelable(cancelable)
        }
        if (!progressDialog!!.isShowing) {
            progressDialog?.isIndeterminate = true
            progressDialog?.show()
            //刷新页面
            val view = layoutInflater.inflate(R.layout.layout_progress, null)
            progressContent = view.findViewById(R.id.txv_tips) as TextView
            if (progressContent != null && !TextUtils.isEmpty(content)) {
                progressContent?.text = content
                progressContent?.visibility = View.VISIBLE
            } else {
                progressContent?.visibility = View.GONE
            }
            progressDialog?.setContentView(view)
        } else if (!TextUtils.isEmpty(content)) {
                progressContent?.text = content
                progressContent?.visibility = View.VISIBLE
        }
    }

    override fun hideProgressDialog() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog?.dismiss()
            progressContent = null
        }
    }

    override fun showToast(stringRes: Int) {
        RxToast.info(getString(stringRes))
    }

    override fun showToast(stringRes: Int, vararg objects: Any?) {
        RxToast.info(getString(stringRes, *objects))
    }

    override fun showToast(string: String) {
        RxToast.info(string)
    }
}