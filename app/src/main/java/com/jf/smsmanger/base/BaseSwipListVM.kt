package com.jf.smsmanger.base

import android.app.Activity
import android.content.Context
import android.databinding.Bindable
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jf.baselibraray.db.BasePresent
import com.jf.baselibraray.event.HttpEvent
import com.jf.baselibraray.net.retrofit.ReqCallback
import com.jf.smsmanger.BR
import com.jf.smsmanger.base.vm.BaseVM
import com.jf.smsmanger.net.BaseNetEntity
import com.jf.smsmanger.utils.Constants
import java.util.ArrayList

abstract class BaseSwipListVM<T:BaseQuickAdapter<out Any,out BaseViewHolder>>(var context:Context):BaseVM<BasePresent>() {
    var nowPage = 1
    var nomoreData = true
    var mCurPage = 1
    /*------------------------------------------DATABIDING----------------------------------------*/
    @get:Bindable
    var adapter:T? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.adapter)
        }

    @get:Bindable
    var layoutManager:LinearLayoutManager? = null
        get() {
            if (field == null){
                field = LinearLayoutManager(context)
                field?.orientation = LinearLayoutManager.VERTICAL
            }
            return field
        }
    set(value) {
        field = value
        notifyPropertyChanged(BR.layoutManager)
    }

    /**刷新状态标记 */
    @get:Bindable
    var refreshing: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.refreshing)
        }

    @Bindable
    var refreshListener: SwipeRefreshLayout.OnRefreshListener? = null
    get() {
        if (field == null) {
            field = SwipeRefreshLayout.OnRefreshListener {
                refreshData(1)
                refreshing = false
            }
        }
        return field
    }
    /*-----------------------------------------------------------------------------*/
    fun initAdapter(recyclerView: RecyclerView) {
        adapter?.setEnableLoadMore(true)
        adapter?.openLoadAnimation()
        adapter?.setOnLoadMoreListener(LoadMoreListener(), recyclerView)
    }

    protected abstract fun refreshData(page: Int)

    inner class LoadMoreListener : BaseQuickAdapter.RequestLoadMoreListener {
        override fun onLoadMoreRequested() {
            if (nomoreData) {
                adapter?.loadMoreEnd(true)
            } else {
                refreshData(mCurPage + 1)
            }
        }
    }

    protected inner class PageCallback<X : BaseNetEntity> constructor(
            var nowPage: Int = 1,
            var activity: Activity? = null
        ) : ReqCallback<ArrayList<X>> {

//        private var promptDialog: PromptDialog? = null

        init {
            if (activity != null) {
//                promptDialog = PromptDialog(activity)
            }
        }

        override fun onReqStart() {
            nomoreData = false
//            if (promptDialog != null) {
//                promptDialog!!.showLoading("加载中")
//            }
        }

        override fun onNetResp(response: ArrayList<X>?) {
//            if (promptDialog != null) {
//                promptDialog!!.dismissImmediately()
//            }
            adapter?.loadMoreComplete()
            //adapter.setUpFetching(false);
            //检查并设置是否还有后续数据
            if (response == null || response.size < Constants.PAGE_SIZE) {
                nomoreData = true
            }
            //更新当前页
            if (response != null && response.size > 0) {
                mCurPage = nowPage
            }
            //刷新数据
            if (nowPage == 1) {
                adapter?.setNewData(response)
            } else {
                adapter?.addData(response!!)
            }
        }

        override fun onReqError(httpEvent: HttpEvent) {
//            if (promptDialog != null) {
//                promptDialog!!.dismissImmediately()
//                promptDialog!!.getDefaultBuilder().loadingDuration(1500)
//                promptDialog!!.showError("加载失败：" + httpEvent.getMessage())
//            }
            adapter?.loadMoreFail()
            //adapter.setUpFetching(false);
            //清理数据
            if (nowPage == 1) {
                adapter?.setNewData(null)
            }
        }
    }
}