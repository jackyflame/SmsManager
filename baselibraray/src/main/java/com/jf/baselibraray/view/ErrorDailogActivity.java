package com.jf.baselibraray.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.jf.baselibraray.R;
import com.jf.baselibraray.net.ParaKeys;
import com.jf.baselibraray.uitls.PermissionUtil;

public class ErrorDailogActivity extends AppCompatActivity implements View.OnClickListener {

    private static String DIALOG_TAG = "";
    private TextView button_ignore;
    private TextView button_setting;
    private TextView txv_tips;

    private boolean isCloseMain = true;
    private boolean cancelable;
    private boolean jumpToPermission;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_errortips_dialog);
        //初始化布局
        initView();
        showData();
    }

    private void initView(){
        findViewById(R.id.lin_bg).setOnClickListener(this);
        button_ignore = findViewById(R.id.button_ignore);
        button_ignore.setOnClickListener(this);
        button_setting = findViewById(R.id.button_setting);
        button_setting.setOnClickListener(this);
        txv_tips = findViewById(R.id.txv_tips);
    }

    private void showData(){
        //忽略按钮是否可见
        boolean isVisible = getIntent().getBooleanExtra(ParaKeys.DIALOG_IGNORE_VISIBLE,false);
        button_ignore.setVisibility(isVisible?View.VISIBLE:View.GONE);
        //是否退出APP
        isCloseMain = getIntent().getBooleanExtra(ParaKeys.DIALOG_ISCLOSEMAIN,true);
        //是否可取消
        cancelable = getIntent().getBooleanExtra(ParaKeys.DIALOG_CANCELABLE,true);
        //是否要跳转到设置页面
        jumpToPermission = getIntent().getBooleanExtra(ParaKeys.DIALOG_AUTHORIGHT_JUMP,true);
        //标题
        String title = getIntent().getStringExtra(ParaKeys.DIALOG_AUTHORIGHT_TITLE);
        if(!TextUtils.isEmpty(title)){
            setTitle(title);
        }
        //取消、忽略按钮文字
        String cancel = getIntent().getStringExtra(ParaKeys.DIALOG_IGNORE_TXT);
        if(!TextUtils.isEmpty(cancel)){
            button_ignore.setText(cancel);
        }else{
            button_ignore.setText(R.string.errordialog_ignore);
        }
        //授权、确认按钮文字
        String confirm = getIntent().getStringExtra(ParaKeys.DIALOG_CONFIRM_TXT);
        if(!TextUtils.isEmpty(confirm)){
            button_setting.setText(confirm);
        }else{
            button_setting.setText(R.string.errordialog_permission);
        }
        //标记
        DIALOG_TAG = getIntent().getStringExtra(ParaKeys.DIALOG_AUTHORIGHT_TAG);
        //内容
        String msg = getIntent().getStringExtra(ParaKeys.DIALOG_AUTHORIGHT_MSG);
        if(!TextUtils.isEmpty(msg)){
            txv_tips.setText(msg);
        }else{
            //权限需求
            String authorightNeed = getIntent().getStringExtra(ParaKeys.DIALOG_AUTHORIGHT_NEED);
            authorightNeed = authorightNeed == null ? "" : "("+authorightNeed+")";
            txv_tips.setText(getString(R.string.errordialog_authority,authorightNeed));
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.lin_bg) {
            if(cancelable){
                realFinish();
            }
        }else if(v.getId() == R.id.button_ignore){
            if(getErrorDailogCallback() != null){
                getErrorDailogCallback().ignoreClick();
            }
            realFinish();
        }else if(v.getId() == R.id.button_setting){
            if(getErrorDailogCallback() != null){
                getErrorDailogCallback().settingClick();
            }
            realFinish();
            if(jumpToPermission){
                PermissionUtil.gotoPermissionSet(this);
            }
        }
    }

    @Override
    public void finish() {
        if(cancelable){
            realFinish();
        }
    }

    public void realFinish(){
        if(mDailogCallback != null){
            mDailogCallback.onDissMiss();
        }
        DIALOG_TAG = "";
        super.finish();
    }

    public interface ErrorDailogCallback{
        void ignoreClick();
        void settingClick();
        void onDissMiss();
    }

    private static ErrorDailogCallback mDailogCallback;

    public static void setErrorDailogCallback(ErrorDailogCallback mDailogCallback) {
        ErrorDailogActivity.mDailogCallback = mDailogCallback;
    }

    public ErrorDailogCallback getErrorDailogCallback() {
        return mDailogCallback;
    }

    public static String getDialogTag(){
        return DIALOG_TAG == null ? "" : DIALOG_TAG;
    }
}
