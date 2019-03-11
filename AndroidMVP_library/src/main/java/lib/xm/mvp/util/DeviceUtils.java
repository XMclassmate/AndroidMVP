package lib.xm.mvp.util;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;


import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import lib.xm.mvp.util.log.LogUtils;

public class DeviceUtils {

    public static final String PREFS_FILE = "device_id.xml";
    public static final String PREFS_DEVICE_ID = "device_id";
    /**
     * 没有网络
     */
    public static final int NETWORKTYPE_INVALID = 0;
    /**
     * wap网络
     */
    public static final int NETWORKTYPE_WAP = 1;
    /**
     * 2G网络
     */
    public static final int NETWORKTYPE_2G = 2;
    /**
     * 3G和3G以上网络，或统称为快速网络
     */
    public static final int NETWORKTYPE_3G = 3;
    /**
     * wifi网络
     */
    public static final int NETWORKTYPE_WIFI = 4;

    /**
     * 手机系统信息
     *
     * @return
     */
    public static String getSystemInfo() {
        return android.os.Build.MODEL + " " + android.os.Build.VERSION.RELEASE;
    }

    /**
     * mac地址
     *
     * @param ctx
     * @return
     */
    public static String getLocalMacAddress(Context ctx) {
        WifiManager wifi = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    /**
     * 获得应用版本名
     *
     * @param ctx
     * @return
     */
    public static String getVersionName(final Context ctx) {
        String version = "1.0";

        PackageManager packageManager = ctx.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(ctx.getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (NameNotFoundException e) {
            LogUtils.e(e.toString());
        }
        return version;
    }

    public static int getVersionCode(final Context ctx) {
        int version = 1;
        PackageManager packageManager = ctx.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(ctx.getPackageName(), 0);
            version = packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            LogUtils.e(e.toString());
        }
        return version;
    }

    /**
     * 判断手机是否有可用网络
     *
     * @param ctx
     * @return
     */
    public static boolean isNetworkEnabled(Context ctx) {
        NetState netState = getNetworkType(ctx);
        return netState != NetState.NET_NO;
    }

    /**
     * 是否是高速网络
     *
     * @param ctx
     * @return
     */
    public static boolean is3GNetwork(Context ctx) {
        ConnectivityManager conMan = null;
        try {
            conMan = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
        if (conMan == null) {
            return false;
        }
        NetworkInfo info = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (info != null) {
            /*
             * 联通的3G为UMTS或HSDPA，移动和联通的2G为GPRS或EDGE，电信的2G为CDMA，电信的3G为EVDO
             * 取值:
                TelephonyManager.NETWORK_TYPE_1xRTT;
                TelephonyManager.NETWORK_TYPE_CDMA;
                TelephonyManager.NETWORK_TYPE_EDGE;
                TelephonyManager.NETWORK_TYPE_EHRPD;
                TelephonyManager.NETWORK_TYPE_EVDO_0;
                TelephonyManager.NETWORK_TYPE_EVDO_A;
                TelephonyManager.NETWORK_TYPE_EVDO_B;
                TelephonyManager.NETWORK_TYPE_GPRS;
                TelephonyManager.NETWORK_TYPE_HSDPA;
                TelephonyManager.NETWORK_TYPE_HSPA;
                TelephonyManager.NETWORK_TYPE_HSPAP;
                TelephonyManager.NETWORK_TYPE_HSUPA;
                TelephonyManager.NETWORK_TYPE_IDEN;
                TelephonyManager.NETWORK_TYPE_LTE;
                TelephonyManager.NETWORK_TYPE_UMTS;
                TelephonyManager.NETWORK_TYPE_UNKNOWN;
            */
            int subtype = info.getSubtype();
            return !(subtype == TelephonyManager.NETWORK_TYPE_1xRTT
                    || subtype == TelephonyManager.NETWORK_TYPE_CDMA
                    || subtype == TelephonyManager.NETWORK_TYPE_EDGE
                    || subtype == TelephonyManager.NETWORK_TYPE_GPRS
                    || subtype == TelephonyManager.NETWORK_TYPE_UNKNOWN);
        }
        return false;
    }


    public static String getPhoneIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        //if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet6Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {

        }
        return "";
    }

    private static String getIMEI(Context ctx) {
        String imei = "";
        try {
            final TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return "";
            }
            imei = tm.getDeviceId();
        } catch (Exception e) {

        }
        return imei;
    }

    /**
     * @param ctx
     * @return
     */
    @Deprecated
    public static String getIMEIorMACAddress(Context ctx) {
        String uniqueId = "";
        uniqueId = getIMEI(ctx);
        if (TextUtils.isEmpty(uniqueId)) {
            uniqueId = getLocalMacAddress(ctx);
        }
        LogUtils.e("uuid", uniqueId);
        return uniqueId;
    }

    public static String getPhoneNumber(Context ctx) {
        TelephonyManager phoneMgr = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
        String phoneNumber = phoneMgr.getLine1Number();
        if (!TextUtils.isEmpty(phoneNumber) && phoneNumber.contains("+86")) {
            phoneNumber = phoneNumber.replace("+86", "");
        }
        return phoneNumber;
    }

    public static boolean getHasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    public static void sendSms(Context ctx, String phone) throws Exception {
        sendSms(ctx, phone, "");
    }

    public static void sendSms(Context ctx, String phone, String content) throws Exception {
        try {
            Uri uri = Uri.parse("smsto:" + phone);
            Intent smsIntent = new Intent(Intent.ACTION_SENDTO, uri);
            smsIntent.putExtra("sms_body", content);
            ctx.startActivity(smsIntent);
        } catch (Exception e) {
            throw e;
        }
    }

    public static void callPhone(Context ctx, String phone, boolean isDirectCall) {
        try {
            Intent phoneIntent = new Intent(isDirectCall ? Intent.ACTION_CALL : Intent.ACTION_DIAL,
                    Uri.parse("tel:" + phone));
            if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            ctx.startActivity(phoneIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断是否为平板
     *
     * @param ctx
     * @return
     */
    public static boolean isTablet(Context ctx) {
        return (ctx.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 判断是否为横屏
     *
     * @param ctx
     * @return
     */
    public static boolean isPortrait(Context ctx) {
        DisplayMetrics dm = ctx.getApplicationContext().getResources().getDisplayMetrics();
        return dm.widthPixels < dm.heightPixels;
    }

    /**
     * 获取当前网络信息
     *
     * @param ctx
     * @return
     */
    public static NetState getNetworkType(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null && ni.isConnected()) {
            switch (ni.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    return NetState.NET_WIFI;
                case ConnectivityManager.TYPE_MOBILE:
                    switch (ni.getSubtype()) {
                        case TelephonyManager.NETWORK_TYPE_GPRS: //联通2g
                        case TelephonyManager.NETWORK_TYPE_CDMA: //电信2g
                        case TelephonyManager.NETWORK_TYPE_EDGE: //移动2g
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                            return NetState.NET_2G;
                        case TelephonyManager.NETWORK_TYPE_EVDO_A: //电信3g
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                            return NetState.NET_3G;
                        case TelephonyManager.NETWORK_TYPE_LTE:
                            return NetState.NET_4G;
                        default:
                            return NetState.NET_UNKOWN;
                    }

                case ConnectivityManager.TYPE_ETHERNET:
                    return NetState.NET_ETHERNET;
                default:
                    return NetState.NET_UNKOWN;
            }
        }
        return NetState.NET_NO;
    }

    /**
     * 获取电信运营商
     *
     * @param ctx
     * @return
     */
    public static TeleState getSimType(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return TeleState.UNKOWN;
        }
        String imsi = tm.getSubscriberId();
        if (imsi != null) {
            if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
                //因为移动网络编号46000下的IMSI已经用完，所以虚拟了一个46002编号，134/159号段使用了此编号 //中国移动
                return TeleState.CHINAMOBILE;
            } else if (imsi.startsWith("46001")) {
                //中国联通
                return TeleState.CHINAUNICOM;
            } else if (imsi.startsWith("46003")) {
                //中国电信
                return TeleState.CHINANET;
            }
        }
        return TeleState.UNKOWN;
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }

    public static int getNetWorkType(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        int mNetWorkType = 0;
        if (networkInfo != null && networkInfo.isConnected()) {
            String type = networkInfo.getTypeName();
            if (type.equalsIgnoreCase("WIFI")) {
                mNetWorkType = NETWORKTYPE_WIFI;
            } else if (type.equalsIgnoreCase("MOBILE")) {
                String proxyHost = android.net.Proxy.getDefaultHost();
                mNetWorkType = TextUtils.isEmpty(proxyHost)
                        ? (isFastMobileNetwork(context) ? NETWORKTYPE_3G : NETWORKTYPE_2G)
                        : NETWORKTYPE_WAP;
            }
        } else {
            mNetWorkType = NETWORKTYPE_INVALID;
        }
        return mNetWorkType;
    }


    public static boolean isFastMobileNetwork(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return false; // ~ 14-64 kbps
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return true; // ~ 400-1000 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return true; // ~ 600-1400 kbps
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return false; // ~ 100 kbps
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return true; // ~ 2-14 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return true; // ~ 700-1700 kbps
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return true; // ~ 1-23 Mbps
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return true; // ~ 400-7000 kbps
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return true; // ~ 1-2 Mbps
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return true; // ~ 5 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return true; // ~ 10-20 Mbps
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return false; // ~25 kbps
            case TelephonyManager.NETWORK_TYPE_LTE:
                return true; // ~ 10+ Mbps
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return false;
            default:
                return false;
        }
    }

    public static String getModel() {
        String model = Build.MODEL;

        if (model == null) {
            model = "";
        }
        return model;
    }

    public static String getAndroidVersion() {
        String sdkVersion = Build.VERSION.RELEASE;
        if (sdkVersion == null) {
            sdkVersion = "";
        }

        return sdkVersion;
    }

    public enum NetState {NET_NO, NET_2G, NET_3G, NET_4G, NET_WIFI, NET_ETHERNET, NET_UNKOWN}

    public enum TeleState {CHINAMOBILE, CHINAUNICOM, CHINANET, UNKOWN}
}