package lib.xm.mvp.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 操作子浮串的工具类
 */

public class StringUtil {


    /**
     * 检查字符串格式
     *
     * @param regex
     * @param str
     * @return
     */
    private static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 获取UUID
     * - 可以作为数据库表的主键
     */
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        String temp = str.replace("-", "");
        temp = temp.substring(0, 26);
        return temp;
    }

    /**
     * 判断字符是否是汉字
     */
    public static boolean checkStrHZ(String str) {
        String telRegex = "[\\u4e00-\\u9fa5]+";
        return str.matches(telRegex);
    }

    /**
     * 判断字符串只由汉字写字母组成
     */
    public static boolean checkStrsHZ(String str) {
        String telRegex = "^[\\u4e00-\\u9fa5]+$";
        return str.matches(telRegex);
    }

    /**
     * 判断字符串只由汉字、数字、大小写字母组成
     */
    public static boolean checkNameLegality(String name) {
        String telRegex = "^[a-zA-Z0-9\\u4e00-\\u9fa5]+$";
        return name.matches(telRegex);
    }

    private static CharacterParser characterParser = CharacterParser.getInstance();

    /**
     * 名字转拼音,取首字母
     *
     * @param name
     * @return
     */
    public static String getSortLetter(String name) {
        String letter = "|";
        if (name == null || name.isEmpty()) {
            return letter;
        }
        //汉字转换成拼音
        String pinyin = characterParser.getSelling(name);
        String sortString = pinyin.substring(0, 1).toUpperCase(Locale.CHINESE);

        // 正则表达式，判断首字母是否是英文字母
        if (sortString.matches("[A-Z]")) {
            letter = sortString.toUpperCase(Locale.CHINESE);
        }
        return letter;
    }

    /**
     * 检测字符串不为null或者""
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 检测字符串是否为null或者""
     */
    public static boolean isEmpty(String str) {
        return (str == null || str.isEmpty());
    }

    /**
     * 将TextView中的字符全角化
     * - 即将所有的数字、字母及标点全部转为全角字符，使它们与汉字同占两个字节，
     * 这样就可以避免由于占位导致的排版混乱问题了。
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375) {
                c[i] = (char) (c[i] - 65248);
            }
        }
        return new String(c);
    }

    /**
     * 获取昵称后两位
     *
     * @param str
     * @return
     */
    public static String getLastName(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (getContentByteLength(str) < 5) {
            return str;
        }
        return str.substring(str.length() - 2);
    }

    /**
     * 得到字符串的字节长度
     *
     * @param content
     * @return
     */
    public static int getContentByteLength(String content) {
        if (content == null || content.length() == 0) {
            return 0;
        }
        int length = 0;
        for (int i = 0; i < content.length(); i++) {
            length += getByteLength(content.charAt(i));
        }
        return length;
    }

    /**
     * 得到几位字节长度
     *
     * @param a
     * @return
     */
    private static int getByteLength(char a) {
        String tmp = Integer.toHexString(a);
        return tmp.length() >> 1;
    }

    /**
     * 转换InputStream 成 string
     *
     * @return String
     * @throws IOException
     */
    public static String inputStream2String(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = -1;
        while ((i = is.read()) != -1) {
            baos.write(i);
        }
        return baos.toString();
    }

    /**
     * 手机号码隐藏中间4位数
     *
     * @param phone 手机号码
     */
    public static String formatPhone(String phone) {
        if (isEmpty(phone)) {
            return "";
        } else if (phone.length() != 11) {
            return phone;
        } else {
            return phone.substring(0, 3) + "****" + phone.substring(7, phone.length());
        }
    }

    public static String getImageName(String filePath) {
        String suffix = "";
        if (filePath.contains(".")) {
            suffix = filePath.substring(filePath.lastIndexOf("."));
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        String imageSize = options.outWidth + "x" + options.outHeight;
        return Calendar.getInstance().getTimeInMillis() + UUID.randomUUID().toString().replaceAll("-", "") + "_" + imageSize + suffix;
    }

    /**
     * 复制字符串到剪贴板
     * @param context
     * @param textContent
     */
    public static void copyTextToClipboard(Context context, String textContent) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData myClip = ClipData.newPlainText("text", textContent);//text是内容
        cm.setPrimaryClip(myClip);
    }

    /**
     * 字符串转unicode
     *
     * @param cn
     * @return
     */
    public static String cnToUnicode(String cn) {
        char[] chars = cn.toCharArray();
        return Integer.toString(chars[0], 16);
    }

    public static boolean isEmail(String strEmail) {
        if (strEmail == null) {
            return false;
        }
        String strPattern = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }

    /**
     * 判断是否为合法手机号
     * @param phoneNumber
     * @return
     */
    public static boolean isMobileNO(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber)) {
            return false;
        }
        phoneNumber = phoneNumber.replaceAll("\\+|\\-", "");
        if (phoneNumber.length() < 11) {
            return false;
        }

        int length = phoneNumber.length();
        phoneNumber = phoneNumber.substring(length - 11, length);

        Pattern p = Pattern.compile("^((111)|(13[0-9])|(14[0-9])|(15[0-9])|(166)|(17[0-9])|(18[0-9])|(19[8-9]))\\d{8}$");
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

    /**
     * 判断字符串里面是否只包含数字和字母
     *
     * @param password
     * @return
     */
    public static boolean isOnlyChatAndNumber(String password) {
        boolean includeNum = false;//包含数字

        boolean includeLetter = false;//包含字母
        boolean includeOther = false;//包含其它字符
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (c >= '0' && c <= '9') {
                includeNum = true;
            } else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                includeLetter = true;
            } else {
                includeOther = true;
            }
        }
        return includeNum && includeLetter && !includeOther;
    }

    /**
     * 格式化 4位一个空格
     *
     * @param value
     * @return
     */
    public static String formatString4(String value) {
        String regex = "(.{4})";
        return value.replaceAll(regex, "$1 ");
    }

    /**
     * 判断字符串是否全为数字
     * @param str
     * @return
     */
    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }
}
