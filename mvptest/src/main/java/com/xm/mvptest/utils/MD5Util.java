package com.xm.mvptest.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by XMclassmate on 2018/5/28.
 */

public class MD5Util {

    public static final String KEY = "u68a7u6850u7406u8d22u79fbu52a8u670du52a1u79d8u94a5";

    /**
     * MD5加密数据(32位加密)
     *
     * @param plainText 要加密的参数
     */
    public static String md5NoPwd(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            // LogUtils.e("result: " + buf.toString());//32位的加密
            // LogUtils.e("result: " +
            // buf.toString().substring(8,24));//16位的加密
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
