package com.jf.smsmanger.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.FragmentTabHost
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TabHost
import android.widget.TextView
import com.haozi.zxwlpro.base.BaseDBActivity
import com.jf.smsmanger.R
import com.jf.smsmanger.databinding.ActivityMainBinding
import com.jf.smsmanger.vm.main.MainVM

class MainActivity : BaseDBActivity<ActivityMainBinding, MainVM>(), TabHost.OnTabChangeListener {

    private lateinit var mTabHost: FragmentTabHost
    private lateinit var tab_home: TabHost.TabSpec
    private lateinit var tab_sms: TabHost.TabSpec
    private lateinit var tab_user: TabHost.TabSpec
    private var currentTab = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
        bindLayout(R.layout.activity_main, MainVM(this),false)
        initTabHost()
    }

    /**
     * 初始化Tabhost
     */
    private fun initTabHost() {
        //获取Tabhost
        mTabHost = findViewById(android.R.id.tabhost)
        mTabHost.setup(this, supportFragmentManager, R.id.fram_realtabcontent)

        //为每一个Tab按钮设置图标、文字和内容
        tab_home = getNewIndicator(R.string.tab_home_menue, R.drawable.main_tab_home_selector)
        tab_sms = getNewIndicator(R.string.tab_sms_menue, R.drawable.main_tab_sms_selector)
        tab_user = getNewIndicator(R.string.tab_user_menue, R.drawable.main_tab_user_selector)

        //将Tab按钮添加进Tab选项卡中
        mTabHost.addTab(tab_home, HomeFragment::class.java, null)
        mTabHost.addTab(tab_sms, SmsFragment::class.java, null)
        mTabHost.addTab(tab_user, UserFragment::class.java, null)

        //设置tabs之间的分隔线不显示
        mTabHost.tabWidget.showDividers = LinearLayout.SHOW_DIVIDER_NONE
        //设置切换监听
        mTabHost.setOnTabChangedListener(this)
        //初始化选择首页
        mTabHost.currentTab= 0
        currentTab = 0
    }


    /**
     * 获取一个新的TabSpec
     * @param titleResid
     * @param iconResid
     */
    @SuppressLint("InflateParams")
    private fun getNewIndicator(titleResid: Int, iconResid: Int): TabHost.TabSpec {
        //获取标题
        var title = ""
        if (titleResid > 0) {
            title = resources.getString(titleResid)
        }
        //实例化新的TabSpec
        val spec = mTabHost.newTabSpec(titleResid.toString() + "")
        //实例化TabSpec的内容VIEW
        val v = LayoutInflater.from(this).inflate(R.layout.layout_home_tab_item, null)
        //设置图标
        val img_tab_icon: ImageView = v.findViewById(R.id.img_tab_icon)
        if (iconResid <= 0) {
            img_tab_icon.visibility = View.GONE
        } else {
            img_tab_icon.setImageResource(iconResid)
        }
        //设置标题
        val txv_tab_title: TextView = v.findViewById(R.id.txv_tab_title)
        if (TextUtils.isEmpty(title)) {
            txv_tab_title.visibility = View.GONE
        } else {
            txv_tab_title.text = title
        }
        return spec.setIndicator(v)
    }

    override fun onTabChanged(tabId: String?) {
        currentTab = mTabHost.currentTab
    }

    companion object {
        var instance:MainActivity? = null
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }
}
