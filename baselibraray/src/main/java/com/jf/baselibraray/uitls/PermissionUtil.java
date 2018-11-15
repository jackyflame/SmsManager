package com.jf.baselibraray.uitls;

import android.app.AppOpsManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.AppOpsManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.text.TextUtils;
import com.jf.baselibraray.BuildConfig;
import com.jf.baselibraray.base.BaseApplication;
import com.jf.baselibraray.net.ParaKeys;
import com.jf.baselibraray.view.ErrorDailogActivity;

import java.lang.reflect.Method;

public class PermissionUtil {

    public static void gotoPermissionSet(Context context) {
        gotoMiuiPermission(context);
    }

    /**
     * 跳转到miui的权限管理页面
     */
    private static void gotoMiuiPermission(Context context) {
        Intent i = new Intent("miui.intent.action.APP_PERM_EDITOR");
        ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        i.setComponent(componentName);
        i.putExtra("extra_pkgname", context.getPackageName());
        try {
            context.startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
            gotoMeizuPermission(context);
        }
    }

    /**
     * 跳转到魅族的权限管理系统
     */
    private static void gotoMeizuPermission(Context context) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            gotoHuaweiPermission(context);
        }
    }

    /**
     * 华为的权限管理页面
     */
    private static void gotoHuaweiPermission(Context context) {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
            intent.setComponent(comp);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            context.startActivity(getAppDetailSettingIntent(context));
        }

    }

    /**
     * 获取应用详情页面intent
     * @return
     */
    private static Intent getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        return localIntent;
    }

    public static boolean selfPermissionGranted(Context context, String permission) {
        // For Android < Android M, self permissions are always granted.
        boolean result = true;
        //获取目标版本号
        int targetSdkVersion = Build.VERSION.SDK_INT;
        try {
            final PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            targetSdkVersion = info.applicationInfo.targetSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (targetSdkVersion >= Build.VERSION_CODES.M) {
                // targetSdkVersion >= Android M, we can
                // use Context#checkSelfPermission
                result = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
            } else {
                // targetSdkVersion < Android M, we have to use PermissionChecker
                result = PermissionChecker.checkSelfPermission(context, permission) == PermissionChecker.PERMISSION_GRANTED;
            }
        }
        return result;
    }

    /**
     * 判断GPS是否开启
     * @param context
     * @return true 表示开启
     */
    public static boolean isGpsOPen(final Context context) {
        return isGpsOPen(context, false);
    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     * @param context networkIsOK（true:网络开启也视为GPS可用,false）
     * @return true 表示开启
     */
    public static boolean isGpsOPen(final Context context, boolean networkIsOK) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        try{
            // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
            boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
            boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (gps || (networkIsOK && network)) {
                return true;
            }
            return gps;
        }catch (Exception e){
            return false;
        }finally {
            locationManager = null;
        }
    }

    /**
     * 强制帮用户打开GPS
     * @param context
     */
    public static final void openGPS(Context context) {
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    private static boolean isGpsOpenOld(Context context) {
        //其中2代表AppOpsManager.OP_GPS
        int checkResult = checkOp(context, 2, AppOpsManager.OPSTR_FINE_LOCATION);
        //OP_FINE_LOCATION = 1;
        int checkResult2 = checkOp(context, 1, AppOpsManager.OPSTR_FINE_LOCATION);
        if (AppOpsManagerCompat.MODE_IGNORED == checkResult || AppOpsManagerCompat.MODE_IGNORED == checkResult2) {
            return false;
        }
        return true;
    }

    /**
     * 检查权限列表
     * @param context
     * @param op 这个值被hide了，去AppOpsManager类源码找，如位置权限  AppOpsManager.OP_GPS==2
     * @param opString 如判断定位权限 AppOpsManager.OPSTR_FINE_LOCATION
     * @return  @see 如果返回值 AppOpsManagerCompat.MODE_IGNORED 表示被禁用了
     */
    public static int checkOp(Context context, int op, String opString) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 19) {
            Object object = context.getSystemService(Context.APP_OPS_SERVICE);
            //Object object = context.getSystemService("appops");
            Class c = object.getClass();
            try {
                Class[] cArg = new Class[3];
                cArg[0] = int.class;
                cArg[1] = int.class;
                cArg[2] = String.class;
                Method lMethod = c.getDeclaredMethod("checkOp", cArg);
                return (Integer) lMethod.invoke(object, op, Binder.getCallingUid(), context.getPackageName());
            } catch (Exception e) {
                e.printStackTrace();
                if (Build.VERSION.SDK_INT >= 23) {
                    return AppOpsManagerCompat.noteOp(context, opString, context.getApplicationInfo().uid, context.getPackageName());
                }
            }
        }
        return -1;
    }

    /*-----------------------------------------------------------显示权限弹出框功能-------------------------------------------------------------*/

    public static void showErrorTips(boolean ignoreAble,boolean cancelable, String title, String msg, ErrorDailogActivity.ErrorDailogCallback callback){
        Intent intent = new Intent(BaseApplication.getInstance(),ErrorDailogActivity.class);
        intent.putExtra(ParaKeys.DIALOG_IGNORE_VISIBLE,ignoreAble);
        intent.putExtra(ParaKeys.DIALOG_CANCELABLE,cancelable);
        if(!TextUtils.isEmpty(title)){
            intent.putExtra(ParaKeys.DIALOG_AUTHORIGHT_TITLE,title);
        }
        if(!TextUtils.isEmpty(msg)){
            intent.putExtra(ParaKeys.DIALOG_AUTHORIGHT_MSG,msg);
        }
        intent.putExtra(ParaKeys.DIALOG_AUTHORIGHT_JUMP,false);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        BaseApplication.getInstance().startActivity(intent);
        ErrorDailogActivity.setErrorDailogCallback(callback);
    }

    public static void showPerrmissionErrorTips(boolean cancelable,String needAuthor,ErrorDailogActivity.ErrorDailogCallback callback){
        showPerrmissionErrorTips(cancelable,null,needAuthor,callback);
    }

    public static void showPerrmissionErrorTips(boolean cancelable,String title, String needAuthor,
                                                ErrorDailogActivity.ErrorDailogCallback callback){
        Bundle bundle = new Bundle();
        bundle.putBoolean(ParaKeys.DIALOG_IGNORE_VISIBLE,true);
        bundle.putBoolean(ParaKeys.DIALOG_ISCLOSEMAIN,false);
        bundle.putBoolean(ParaKeys.DIALOG_CANCELABLE,cancelable);
        if(!TextUtils.isEmpty(title)){
            bundle.putString(ParaKeys.DIALOG_AUTHORIGHT_TITLE,title);
        }
        if(!TextUtils.isEmpty(needAuthor)){
            bundle.putString(ParaKeys.DIALOG_AUTHORIGHT_NEED,needAuthor);
        }
        Intent intent = new Intent(BaseApplication.getInstance(),ErrorDailogActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(bundle);
        BaseApplication.getInstance().startActivity(intent);
        ErrorDailogActivity.setErrorDailogCallback(callback);
    }
}
