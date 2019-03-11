package lib.xm.mvp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 时间日期格式化工具类
 */

public class TimeUtil {

    /**
     * 获取当前时间
     * 时间格式：2016-06-01 12:00:00
     */
    public static String getCurrentTime() {
        long mTime = System.currentTimeMillis();
        return getCurrentTime(mTime);
    }

    /**
     * 格式化时间
     * 时间格式：2016-06-01 12:00:00
     */
    public static String getCurrentTime(long mTime) {
        Date date = new Date(mTime);
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(date);
        return time.substring(0, time.length());
    }

    /**
     * 时间戳转String
     * @param time
     * @param pattern
     * @return
     */
    public static String long2String(long time, String pattern){
        if (StringUtil.isEmpty(pattern)) {
            return time + "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date(time));
    }

    /**
     * String转String
     * @param srcPattern
     * @param descPattern
     * @param srcTime
     * @return
     */
    public static String string2String(String srcPattern, String descPattern, String srcTime){
        if (StringUtil.isEmpty(srcPattern) || StringUtil.isEmpty(descPattern) || StringUtil.isEmpty(srcTime)){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(srcPattern);
        try {
            Date date = sdf.parse(srcTime);
            sdf = new SimpleDateFormat(descPattern);
            return sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return srcTime;
    }

    /**
     * 将秒格式化成 2分3秒
     *
     * @param reduce
     * @return
     */
    public static String getFormatTimeMSChine(int reduce) {
        try {
            int minute = reduce / 60 % 60;
            int second = reduce % 60;

            return (minute > 9 ? minute : ("0" + minute)) + "分"
                    + (second > 9 ? second : ("0" + second));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
